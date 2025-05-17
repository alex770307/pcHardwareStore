package org.pchardwarestore.service.accountService.userService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.accountDto.UpdateUserRequest;
import org.pchardwarestore.dto.accountDto.UpdateUserRequestForAdmin;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.Role;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.UserRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.AccountConverter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserServiceTest {

    private UserRepository userRepository;
    private AccountConverter accountConverter;
    private UpdateUserService updateUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        accountConverter = mock(AccountConverter.class);
        updateUserService = new UpdateUserService(userRepository, accountConverter);
    }

    @Test
    void updateUser_shouldUpdateUserFieldsAndReturnResponse() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("newPass");

        User user = new User();
        UserResponse response = new UserResponse();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(accountConverter.fromUser(user)).thenReturn(response);

        UserResponse result = updateUserService.updateUser(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(userRepository).findByEmail(request.getEmail());
        verify(userRepository).save(user);
        verify(accountConverter).fromUser(user);
    }

    @Test
    void updateUser_shouldThrowException_whenEmailIsMissing() {
        UpdateUserRequest request = new UpdateUserRequest();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateUserService.updateUser(request));

        assertEquals("Email must be provided to update user", exception.getMessage());
        verifyNoInteractions(userRepository, accountConverter);
    }

    @Test
    void updateUser_shouldThrowNotFoundException_whenUserNotFound() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("missing@example.com");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> updateUserService.updateUser(request));

        assertEquals("User with email missing@example.com not found", exception.getMessage());
        verify(userRepository).findByEmail(request.getEmail());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(accountConverter);
    }

    @Test
    void updateUserForAdmin_shouldUpdateRoleAndReturnResponse() {
        UpdateUserRequestForAdmin request = new UpdateUserRequestForAdmin();
        request.setEmail("admin@example.com");
        request.setRole("ADMIN");

        User user = new User();
        UserResponse response = new UserResponse();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(accountConverter.fromUser(user)).thenReturn(response);

        UserResponse result = updateUserService.updateUserForAdmin(request);

        assertNotNull(result);
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals(response, result);

        verify(userRepository).findByEmail(request.getEmail());
        verify(userRepository).save(user);
        verify(accountConverter).fromUser(user);
    }

    @Test
    void updateUserForAdmin_shouldThrowException_whenEmailIsMissing() {
        UpdateUserRequestForAdmin request = new UpdateUserRequestForAdmin();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateUserService.updateUserForAdmin(request));

        assertEquals("Email must be provided to update user", exception.getMessage());
        verifyNoInteractions(userRepository, accountConverter);
    }

    @Test
    void updateUserForAdmin_shouldThrowNotFoundException_whenUserNotFound() {
        UpdateUserRequestForAdmin request = new UpdateUserRequestForAdmin();
        request.setEmail("missing@example.com");
        request.setRole("ADMIN");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> updateUserService.updateUserForAdmin(request));

        assertEquals("User with email missing@example.com not found", exception.getMessage());
        verify(userRepository).findByEmail(request.getEmail());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(accountConverter);
    }
}