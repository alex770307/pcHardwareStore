package org.pchardwarestore.service.accountService.userService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.accountDto.AddUserRequest;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.Role;
import org.pchardwarestore.entity.accountEntity.Status;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.UserRepository;
import org.pchardwarestore.service.accountService.confirmationCodeService.ConfirmationCodeService;
import org.pchardwarestore.service.exception.AlreadyExistException;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.AccountConverter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationUserServiceTest {

    private UserRepository userRepository;
    private AccountConverter converter;
    private ConfirmationCodeService confirmationCodeService;
    private PasswordEncoder passwordEncoder;
    private RegistrationUserService registrationUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        converter = mock(AccountConverter.class);
        confirmationCodeService = mock(ConfirmationCodeService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        registrationUserService = new RegistrationUserService(userRepository, converter, confirmationCodeService, passwordEncoder);
    }

    @Test
    void registration_shouldRegisterUserSuccessfully() {
        AddUserRequest request = new AddUserRequest();
        request.setEmail("test@example.com");
        request.setPassword("plainPassword");

        User newUser = new User();
        User savedUser = new User();
        UserResponse expectedResponse = new UserResponse();

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(converter.toUser(request)).thenReturn(newUser);
        when(passwordEncoder.encode("plainPassword")).thenReturn("hashedPassword");
        when(userRepository.save(newUser)).thenReturn(savedUser);
        when(converter.fromUser(newUser)).thenReturn(expectedResponse);

        UserResponse actualResponse = registrationUserService.registration(request);

        assertNotNull(actualResponse);
        verify(userRepository).existsByEmail("test@example.com");
        verify(converter).toUser(request);
        assertEquals(Role.USER, newUser.getRole());
        assertEquals(Status.NOT_CONFIRMED, newUser.getStatus());
        assertEquals("hashedPassword", newUser.getHashPassword());
        verify(userRepository).save(newUser);
        verify(confirmationCodeService).confirmationCodeHandle(newUser);
        verify(converter).fromUser(newUser);
    }

    @Test
    void registration_shouldThrowAlreadyExistException_whenEmailAlreadyExists() {
        AddUserRequest request = new AddUserRequest();
        request.setEmail("duplicate@example.com");

        when(userRepository.existsByEmail("duplicate@example.com")).thenReturn(true);

        AlreadyExistException exception = assertThrows(AlreadyExistException.class,
                () -> registrationUserService.registration(request));

        assertEquals("User with email duplicate@example.com already exist", exception.getMessage());
        verify(userRepository).existsByEmail("duplicate@example.com");
        verifyNoMoreInteractions(userRepository, converter, confirmationCodeService, passwordEncoder);
    }

    @Test
    void confirmationEmail_shouldConfirmUserAndReturnResponse() {
        String code = "abc123";
        User user = new User();
        UserResponse response = new UserResponse();

        when(confirmationCodeService.confirmUserByCode(code)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(converter.fromUser(user)).thenReturn(response);

        UserResponse actual = registrationUserService.confirmationEmail(code);

        assertSame(response, actual);
        assertEquals(Status.CONFIRMED, user.getStatus());
        verify(confirmationCodeService).confirmUserByCode(code);
        verify(userRepository).save(user);
        verify(converter).fromUser(user);
    }

    @Test
    void renewCode_shouldResendConfirmationCode_whenUserExists() {
        String email = "resend@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        boolean result = registrationUserService.renewCode(email);

        assertTrue(result);
        verify(userRepository).findByEmail(email);
        verify(confirmationCodeService).confirmationCodeHandle(user);
    }

    @Test
    void renewCode_shouldThrowNotFoundException_whenUserNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> registrationUserService.renewCode(email));

        assertEquals("User with email: notfound@example.com not found", exception.getMessage());
        verify(userRepository).findByEmail(email);
        verifyNoMoreInteractions(userRepository, confirmationCodeService, converter, passwordEncoder);
    }
}