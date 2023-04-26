package group1.itschool.onlinebookstore.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDTO implements Serializable {

    @NotNull
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;
    @NotNull(message = "Email is a mandatory field")
    private String email;
    @NotNull
    @Size(max = 100, message = "Full name must be less than or equal to 100 characters")
    private String fullName;
    @NotNull
    @Past(message = "Date of birth should be in the past")
    private LocalDate dateOfBirth;
    @NotNull
    @Min(value = 50, message = "Interests maximum 50 characters")
    private String interests;
    @NotNull
    private LocalDate accountCreationDate;
}