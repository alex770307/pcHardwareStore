package org.pchardwarestore.controller.accountController;

import lombok.RequiredArgsConstructor;

import org.pchardwarestore.controller.accountController.api.AdminApi;
import org.pchardwarestore.dto.accountDto.UpdateUserRequestForAdmin;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.service.accountService.userService.DeleteUserService;
import org.pchardwarestore.service.accountService.userService.FindUserService;
import org.pchardwarestore.service.accountService.userService.UpdateUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController implements AdminApi {

    private final FindUserService findUserService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteService;

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

    //найти всех по фамилии
    @GetMapping("/lastname")
    public ResponseEntity<List<UserResponse>> findUserByLastName(String lastName){
        return ResponseEntity.ok(findUserService.findUserByLastName(lastName));
    };

    //обновить роль пользователя
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUserForAdmin(@RequestBody UpdateUserRequestForAdmin request){
        return ResponseEntity
                .status(201)
                .body(updateUserService.updateUserForAdmin(request));
    }

    //* удаление записи
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id){
        return deleteService.deleteUser(id);
    };

}
