package group1.itschool.onlinebookstore.models.dto.Auth;

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