package org.pchardwarestore.dto.accountDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserRequest {

    @NotBlank(message = "First name is required and must be not blank")
    @Size(min = 3, max = 25, message = "First name length not correct")
    private String firstName;

    @Size(max = 25, message = "Last name too long")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password length must be between 6 and 100 characters")
    private String password;
}
