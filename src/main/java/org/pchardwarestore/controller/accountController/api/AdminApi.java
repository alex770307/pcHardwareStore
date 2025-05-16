package org.internetshop45efs.controller.api;

import org.internetshop45efs.dto.UserResponseDto;
import org.internetshop45efs.dto.UserUpdateRequestDto;
import org.internetshop45efs.entity.User;
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
    public ResponseEntity<List<UserResponseDto>> findAll();


    // * обновить данные от имени пользователь (пользователь хочет
    // поменять какие-то данные в своем профиле)
//    @PutMapping("/update")
//    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserUpdateRequestDto request);


    //* удаление записи
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Integer id);


}
