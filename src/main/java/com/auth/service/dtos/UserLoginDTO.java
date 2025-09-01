package com.auth.service.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @Pattern(regexp = "^[a-zA-Z0-9_'^&/+-]+(?:\\.[a-zA-Z0-9_'^&/+-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$", message = "Invalid Email")
    @Schema(description = "user email", example = "user@email.com")
    private String email;

    @Size(min = 6, max = 12, message = "Size must be between 6 and 12")
    @Schema(description = "user password", example = "123456")
    private String password;

}
