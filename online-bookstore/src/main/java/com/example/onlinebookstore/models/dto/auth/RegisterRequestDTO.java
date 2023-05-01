package com.example.onlinebookstore.models.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    String password;
}