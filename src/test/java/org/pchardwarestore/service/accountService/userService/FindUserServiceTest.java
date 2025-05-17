package org.pchardwarestore.service.accountService.userService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.UserRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.AccountConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindUserServiceTest {

    private UserRepository userRepository;
    private AccountConverter accountConverter;
    private FindUserService findUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        accountConverter = mock(AccountConverter.class);
        findUserService = new FindUserService(userRepository, accountConverter);
    }

    @Test
    void findFullDetailUsers_shouldReturnListOfUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = findUserService.findFullDetailUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void findAllUsers_shouldReturnListOfUserResponses() {
        List<User> users = Arrays.asList(new User(), new User());
        List<UserResponse> responses = Arrays.asList(new UserResponse(), new UserResponse());

        when(userRepository.findAll()).thenReturn(users);
        when(accountConverter.fromUsers(users)).thenReturn(responses);

        List<UserResponse> result = findUserService.findAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
        verify(accountConverter).fromUsers(users);
    }

    @Test
    void findUserById_shouldReturnUserResponse_whenUserExists() {
        Long id = 1L;
        User user = new User();
        UserResponse response = new UserResponse();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(accountConverter.fromUser(user)).thenReturn(response);

        UserResponse result = findUserService.findUserById(id);

        assertNotNull(result);
        verify(userRepository).findById(id);
        verify(accountConverter).fromUser(user);
    }

    @Test
    void findUserById_shouldThrowNotFoundException_whenUserNotFound() {
        Long id = 2L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> findUserService.findUserById(id));

        assertEquals("User with ID 2 not found", exception.getMessage());
        verify(userRepository).findById(id);
        verifyNoMoreInteractions(accountConverter);
    }

    @Test
    void findUserByEmail_shouldReturnUserResponse_whenUserExists() {
        String email = "user@example.com";
        User user = new User();
        UserResponse response = new UserResponse();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(accountConverter.fromUser(user)).thenReturn(response);

        UserResponse result = findUserService.findUserByEmail(email);

        assertNotNull(result);
        verify(userRepository).findByEmail(email);
        verify(accountConverter).fromUser(user);
    }

    @Test
    void findUserByEmail_shouldThrowNotFoundException_whenUserNotFound() {
        String email = "missing@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> findUserService.findUserByEmail(email));

        assertEquals("User with email missing@example.com not found", exception.getMessage());
        verify(userRepository).findByEmail(email);
        verifyNoMoreInteractions(accountConverter);
    }

    @Test
    void findUserByLastName_shouldReturnListOfUserResponses() {
        String lastName = "Smith";
        List<User> users = Arrays.asList(new User(), new User());
        List<UserResponse> responses = Arrays.asList(new UserResponse(), new UserResponse());

        when(userRepository.findByLastName(lastName)).thenReturn(users);
        when(accountConverter.fromUsers(users)).thenReturn(responses);

        List<UserResponse> result = findUserService.findUserByLastName(lastName);

        assertEquals(2, result.size());
        verify(userRepository).findByLastName(lastName);
        verify(accountConverter).fromUsers(users);
    }
}