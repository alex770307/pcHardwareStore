package org.pchardwarestore.controller.accountController;

import lombok.RequiredArgsConstructor;

import org.pchardwarestore.controller.accountController.api.PublicApi;
import org.pchardwarestore.dto.accountDto.AddUserRequest;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.service.accountService.userService.RegistrationUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class PublicController implements PublicApi {

    private final RegistrationUserService registrationUserService;
    //*  добавить нового пользователя
    @PostMapping("/new")
    public ResponseEntity<UserResponse> addNewUser(@RequestBody AddUserRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registrationUserService.registration(request));
    }

    // Подтверждение пользователя по коду
    @GetMapping("/confirm")
    public ResponseEntity<UserResponse> confirmUser(@RequestParam String code) {
        UserResponse userResponse = registrationUserService.confirmationEmail(code);
        return ResponseEntity.ok(userResponse);
    }
}
