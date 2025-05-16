package org.pchardwarestore.service.accountService.userService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pchardwarestore.dto.accountDto.UpdateUserRequest;
import org.pchardwarestore.dto.accountDto.UpdateUserRequestForAdmin;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.Role;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.UserRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.AccountConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService {
    private final UserRepository repository;
    private final AccountConverter converter;

    @Transactional
    public UserResponse updateUser(UpdateUserRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email must be provided to update user");
        }
        String email = request.getEmail();
        User userByEmail = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            userByEmail.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            userByEmail.setLastName(request.getLastName());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            userByEmail.setHashPassword(request.getPassword());
        }
        repository.save(userByEmail);
        return converter.fromUser(userByEmail);
    }

    @Transactional
    public UserResponse updateUserForAdmin(UpdateUserRequestForAdmin request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email must be provided to update user");
        }
        String email = request.getEmail();
        User userByEmail = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
        if (request.getRole() !=null && !request.getRole().isBlank()) {
            userByEmail.setRole(Role.valueOf(request.getRole()));
        }
        repository.save(userByEmail);
        return converter.fromUser(userByEmail);
    }
}
