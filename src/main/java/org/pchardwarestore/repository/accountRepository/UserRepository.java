package org.pchardwarestore.repository.accountRepository;

import org.pchardwarestore.entity.accountEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByLastName(String lastName);

    boolean existsByEmail(String email);
}
