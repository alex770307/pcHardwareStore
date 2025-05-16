package org.pchardwarestore.dto.accountDto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequestForAdmin {

    @Email(message = "Invalid email format")

    private String email;
    private String role;

}
