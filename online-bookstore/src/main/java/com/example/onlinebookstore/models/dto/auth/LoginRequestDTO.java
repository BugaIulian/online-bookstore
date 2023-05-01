package com.example.onlinebookstore.models.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @Email
    private String email;
    @NotBlank
    private String password;
}