package group1.itschool.onlinebookstore.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDTO implements Serializable {
    @NotNull
    @NotEmpty
    private String productName;
}