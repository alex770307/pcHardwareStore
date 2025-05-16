package org.pchardwarestorefour.service.accountService.userService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pchardwarestorefour.dto.accountDto.AddUserRequest;
import org.pchardwarestorefour.dto.accountDto.UserResponse;
import org.pchardwarestorefour.entity.accountEntity.Role;
import org.pchardwarestorefour.entity.accountEntity.Status;
import org.pchardwarestorefour.entity.accountEntity.User;
import org.pchardwarestorefour.repository.accountRepository.UserRepository;
import org.pchardwarestorefour.service.accountService.confirmationCodeService.ConfirmationCodeService;
import org.pchardwarestorefour.service.exception.AlreadyExistException;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.util.AccountConverter;
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
        //TODO Добавил 07.05.25
        newUser.setHashPassword(passwordEncoder.encode(request.getPassword()));
        //TODO Добавил 07.05.25
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
