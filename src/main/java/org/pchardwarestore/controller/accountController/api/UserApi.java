package org.pchardwarestore.controller.accountController.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.pchardwarestore.dto.accountDto.ErrorResponse;
import org.pchardwarestore.dto.accountDto.UpdateUserRequest;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
public interface UserApi {

    //*найти пользователя по ID
    @Operation(summary = "Получение информации о пользователе по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id);

    //*найти пользователя по email
    @GetMapping()
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email);

    // * обновить данные от имени пользователь (пользователь хочет
    // поменять какие-то данные в своем профиле)
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request);

}
