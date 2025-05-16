package org.pchardwarestore.repository.accountRepository;

import org.pchardwarestore.entity.accountEntity.ConfirmationCode;
import org.pchardwarestore.entity.accountEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {
    Optional<ConfirmationCode> findByCode(String code);

    Optional<ConfirmationCode> findByCodeAndExpireDateTimeAfter(String code, LocalDateTime currentDateTime);

    List<ConfirmationCode> findByUser(User user);
}
