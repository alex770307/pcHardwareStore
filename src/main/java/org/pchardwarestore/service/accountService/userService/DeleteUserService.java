package org.pchardwarestorefour.service.accountService.userService;

import lombok.RequiredArgsConstructor;
import org.pchardwarestorefour.entity.accountEntity.User;
import org.pchardwarestorefour.repository.accountRepository.UserRepository;
import org.pchardwarestorefour.service.util.AccountConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService {
    private final UserRepository repository;
    private final AccountConverter converter;

    public boolean deleteUser(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
       repository.deleteById(id);
        return true;
    }
    public boolean deleteByEmail(String email) {
        if (!repository.existsByEmail(email)) {
            return false;
        }
        User userForDelete = repository.findByEmail(email).get();
        repository.delete(userForDelete);
        return true;
    }
}
