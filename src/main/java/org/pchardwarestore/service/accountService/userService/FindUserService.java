package org.pchardwarestore.service.accountService.userService;

import lombok.RequiredArgsConstructor;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.UserRepository;

import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.util.AccountConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindUserService {

    private final UserRepository repository;
    private final AccountConverter converter;

    public List<User> findFullDetailUsers() {
        return repository.findAll();
    }

    public List<UserResponse> findAllUsers() {
        List<User> users = repository.findAll();
        List<UserResponse> responses = converter.fromUsers(users);
        return responses;
    }

    public UserResponse findUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
        return converter.fromUser(user);
    }

    public UserResponse findUserByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));

        return converter.fromUser(user);
    }

    public List<UserResponse>  findUserByLastName(String lastName) {
        List<User> users = repository.findByLastName(lastName);
        List<UserResponse> responses = converter.fromUsers(users);
        return responses;
    }
}
