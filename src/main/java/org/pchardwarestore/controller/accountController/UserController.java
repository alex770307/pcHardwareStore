package org.pchardwarestorefour.controller.accountController;

import lombok.RequiredArgsConstructor;
import org.pchardwarestorefour.dto.accountDto.AddUserRequest;
import org.pchardwarestorefour.dto.accountDto.UpdateUserRequest;
import org.pchardwarestorefour.dto.accountDto.UserResponse;
import org.pchardwarestorefour.entity.accountEntity.User;
import org.pchardwarestorefour.service.accountService.userService.DeleteUserService;
import org.pchardwarestorefour.service.accountService.userService.FindUserService;
import org.pchardwarestorefour.service.accountService.userService.RegistrationUserService;
import org.pchardwarestorefour.service.accountService.userService.UpdateUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final RegistrationUserService registrationUserService;
    private final FindUserService findUserService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;

    //*  добавить нового пользователя
    @PostMapping("/new")
    public ResponseEntity<UserResponse> addNewUser(@RequestBody AddUserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registrationUserService.registration(request));
    }

    //* найти всех пользователей (полная информация - для ADMIN)
    @GetMapping("/full")
    public ResponseEntity<List<User>> findAllFullDetails(){
        return ResponseEntity.ok(findUserService.findFullDetailUsers());
    };

    //* найти всех пользователей (ограниченная информация - для MANAGER)
    @GetMapping("/manager/all")
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(findUserService.findAllUsers());
    };

    //*найти пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id){
        return ResponseEntity.ok(findUserService.findUserById(id));
    };

    //*найти пользователя по email
    @GetMapping()
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(findUserService.findUserByEmail(email));
    };

    //найти всех по фамилии
    @GetMapping("/lastname")
    public ResponseEntity<List<UserResponse>> findUserByLastName(String lastName){
        return ResponseEntity.ok(findUserService.findUserByLastName(lastName));
    };

    // * обновить данные от имени пользователь (пользователь хочет
    // поменять какие-то данные в своем профиле)
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updateUserService.updateUser(request));
    };

    //* удаление записи
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id){
        return deleteUserService.deleteUser(id);
    };

    //TODO Добавил 07.05.25
// Подтверждение пользователя по коду
    @GetMapping("/confirm")
    public ResponseEntity<UserResponse> confirmUser(@RequestParam String code) {
        UserResponse userResponse = registrationUserService.confirmationEmail(code);
        return ResponseEntity.ok(userResponse);
    }
    //TODO Добавил 07.05.25
}
