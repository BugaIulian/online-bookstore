package group1.itschool.onlinebookstore.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorDTO implements Serializable {
    @NotNull
    private String name;
}
