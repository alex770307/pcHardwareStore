package org.pchardwarestore.controller.accountController.api;


import org.pchardwarestore.dto.accountDto.UpdateUserRequestForAdmin;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
public interface AdminApi {

    //* найти всех пользователей (полная информация - для ADMIN)
    @GetMapping("/full")
    public ResponseEntity<List<User>> findAllFullDetails();

    //* найти всех пользователей (ограниченная информация - для MANAGER)
    @GetMapping("/manager/all")
    public ResponseEntity<List<UserResponse>> findAll();

    //найти всех по фамилии
    @GetMapping("/lastname")
    public ResponseEntity<List<UserResponse>> findUserByLastName(String lastName);

    //обновить роль пользователя
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUserForAdmin(@RequestBody UpdateUserRequestForAdmin request);


    //* удаление записи
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id);


}
