package org.pchardwarestore.service.accountService.confirmationCodeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pchardwarestore.entity.accountEntity.ConfirmationCode;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.ConfirmationCodeRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.mail.MailUtil;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfirmationCodeServiceTest {

    private ConfirmationCodeRepository repository;
    private MailUtil mailUtil;
    private ConfirmationCodeService confirmationCodeService;

    @BeforeEach
    void setUp() {
        repository = mock(ConfirmationCodeRepository.class);
        mailUtil = mock(MailUtil.class);
        confirmationCodeService = new ConfirmationCodeService(repository, mailUtil);
    }

    @Test
    void confirmationCodeHandle_shouldSaveCodeAndSendEmail() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");

        confirmationCodeService.confirmationCodeHandle(user);

        verify(repository, times(1)).save(any(ConfirmationCode.class));
        verify(mailUtil, times(1)).sendEmail(
                eq("John"),
                eq("Doe"),
                contains("http://localhost:8080/api/users/confirm?code="),
                eq("Code confirmation email"),
                eq("john@example.com")
        );
    }

    @Test
    void confirmUserByCode_shouldSetConfirmedAndReturnUser() {
        String code = UUID.randomUUID().toString();

        User user = new User();
        ConfirmationCode confirmationCode = new ConfirmationCode();
        confirmationCode.setUser(user);
        confirmationCode.setConfirmed(false);

        when(repository.findByCode(code)).thenReturn(Optional.of(confirmationCode));

        User result = confirmationCodeService.confirmUserByCode(code);

        assertSame(user, result);
        assertTrue(confirmationCode.isConfirmed());
        verify(repository).findByCode(code);
        verify(repository).save(confirmationCode);
    }

    @Test
    void confirmUserByCode_shouldThrowNotFoundException_whenCodeNotFound() {
        String invalidCode = "invalid-code";

        when(repository.findByCode(invalidCode)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> confirmationCodeService.confirmUserByCode(invalidCode));

        assertEquals("Confirmation code : " + invalidCode + " not found", exception.getMessage());
        verify(repository).findByCode(invalidCode);
        verifyNoMoreInteractions(repository, mailUtil);
    }
}