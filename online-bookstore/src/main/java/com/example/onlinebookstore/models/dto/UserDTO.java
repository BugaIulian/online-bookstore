package com.example.onlinebookstore.models.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDTO implements Serializable {

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;
    @NotNull(message = "Email is a mandatory field")
    @NotEmpty(message = "Email can't be empty")
    private String email;
    @NotNull
    @NotEmpty
    private String fullName;
    @Past(message = "Date of birth should be in the past")
    private LocalDate dateOfBirth;
    @NotNull
    private String interests;
}