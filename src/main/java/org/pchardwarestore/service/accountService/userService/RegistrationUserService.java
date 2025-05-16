package org.pchardwarestore.service.accountService.userService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationUserService {
    private final UserRepository repository;
    private final AccountConverter converter;
    private final ConfirmationCodeService confirmationCodeService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse registration(AddUserRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("User with email " + request.getEmail() + " already exist");
        }
        User newUser = converter.toUser(request);
        newUser.setRole(Role.USER);
        newUser.setStatus(Status.NOT_CONFIRMED);
        newUser.setHashPassword(passwordEncoder.encode(request.getPassword()));

        repository.save(newUser);
        confirmationCodeService.confirmationCodeHandle(newUser);

        return converter.fromUser(newUser);
    }

    @Transactional
    public UserResponse confirmationEmail(String code){
        User user = confirmationCodeService.confirmUserByCode(code);
        user.setStatus(Status.CONFIRMED);
        repository.save(user);
        return converter.fromUser(user);
    }

    public boolean renewCode(String email){
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));
        confirmationCodeService.confirmationCodeHandle(user);
        return true;
    }
}
