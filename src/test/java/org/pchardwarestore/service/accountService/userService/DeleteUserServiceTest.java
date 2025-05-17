package org.pchardwarestore.service.accountService.userService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.UserRepository;
import org.pchardwarestore.service.util.AccountConverter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserServiceTest {

    private UserRepository userRepository;
    private AccountConverter converter;
    private DeleteUserService deleteUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        converter = mock(AccountConverter.class);
        deleteUserService = new DeleteUserService(userRepository, converter);
    }

    @Test
    void deleteUser_shouldReturnTrue_whenUserExistsById() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        boolean result = deleteUserService.deleteUser(userId);

        assertTrue(result);
        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUser_shouldReturnFalse_whenUserDoesNotExistById() {
        Long userId = 2L;
        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = deleteUserService.deleteUser(userId);

        assertFalse(result);
        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteByEmail_shouldReturnTrue_whenUserExistsByEmail() {
        String email = "user@example.com";
        User mockUser = new User();

        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        boolean result = deleteUserService.deleteByEmail(email);

        assertTrue(result);
        verify(userRepository).existsByEmail(email);
        verify(userRepository).findByEmail(email);
        verify(userRepository).delete(mockUser);
    }

    @Test
    void deleteByEmail_shouldReturnFalse_whenUserDoesNotExistByEmail() {
        String email = "notfound@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        boolean result = deleteUserService.deleteByEmail(email);

        assertFalse(result);
        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).delete(any());
    }
}