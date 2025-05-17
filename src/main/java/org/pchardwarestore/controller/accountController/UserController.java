package org.pchardwarestore.controller.accountController;


import lombok.RequiredArgsConstructor;
import org.pchardwarestore.controller.accountController.api.UserApi;
import org.pchardwarestore.dto.accountDto.UpdateUserRequest;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.service.accountService.userService.FindUserService;
import org.pchardwarestore.service.accountService.userService.RegistrationUserService;
import org.pchardwarestore.service.accountService.userService.UpdateUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final FindUserService findUserService;
    private final UpdateUserService updateUserService;
    private final RegistrationUserService registrationUserService;

    //*найти пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(findUserService.findUserById(id));
    }

    //*найти пользователя по email
    @GetMapping()
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(findUserService.findUserByEmail(email));
    }

    // * обновить данные от имени пользователь (пользователь хочет
    // поменять какие-то данные в своем профиле)
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updateUserService.updateUser(request));
    }
}
