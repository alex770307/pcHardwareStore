package org.pchardwarestore.controller.accountController.api;


import org.pchardwarestore.dto.accountDto.UpdateUserRequestForAdmin;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
public interface AdminApi {

    @GetMapping("/full")
    public ResponseEntity<List<User>> findAllFullDetails();

    @GetMapping("/manager/all")
    public ResponseEntity<List<UserResponse>> findAll();

    @GetMapping("/lastname")
    public ResponseEntity<List<UserResponse>> findUserByLastName(String lastName);

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUserForAdmin(@RequestBody UpdateUserRequestForAdmin request);

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id);


}
