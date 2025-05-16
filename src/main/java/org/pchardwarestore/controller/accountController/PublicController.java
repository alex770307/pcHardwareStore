package org.internetshop45efs.controller;

import lombok.RequiredArgsConstructor;
import org.internetshop45efs.controller.api.PublicApi;
import org.internetshop45efs.dto.UserRequestDto;
import org.internetshop45efs.dto.UserResponseDto;
import org.internetshop45efs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class PublicController implements PublicApi {

    private final UserService service;


    //*  добавить нового пользователя

    @PostMapping("/new")
    public ResponseEntity<UserResponseDto> addNewUser(@RequestBody UserRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registration(request));
    };


}
