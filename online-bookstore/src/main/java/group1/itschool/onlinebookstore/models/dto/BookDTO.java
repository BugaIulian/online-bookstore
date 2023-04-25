package group1.itschool.onlinebookstore.models.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {

    @NotNull
    @Size(min = 2, max = 100, message = "Should contain between 2 and 100 letters.")
    private String title;
    @NotNull
    @Size(min = 2, max = 30, message = "Should contain between 2 and 30 letters.")
    private String author;
    @NotNull
    @Size(min = 2, max = 50, message = "Should contain between 2 and 50 letters.")
    private String publisher;
    @PastOrPresent
    private LocalDate publicationDate;
    @NotNull
    @Size(min = 10, max = 13, message = "Should contain between 10 and 13 numbers.")
    private int codeISBN;
    @NotNull
    @Size(min = 4, max = 50, message = "Genre must contain at least 4 letters.")
    private String genre;
    @NotNull
    @Size(min = 10, max = 100, message = "Should contain between 10 and 100 letters.")
    private String synopsis;
    @NotNull
    @Size(min = 10, max = 100, message = "Should contain between 10 and 100 letters.")
    private String coverDesign;
    @NotNull
    @Size(min = 5, max = 10000, message = "The minimum page count is 5 and the maximum is 10.000 pages for a book.")
    private int pageCount;
    @NotNull
    @Min(value = 1, message = "Value must greater or equal to 1.")
    private String language;
    @NotNull
    private String format;
    @DecimalMin(value = "0.0", message = "Minimum price can't be 0.0.")
    private double price;
    @NotNull
    private String review;
    @NotBlank
    @Pattern(regexp = "^\\\\d{6}$")
    private String qrCode;
    @NotNull
    @Min(value = 0, message = "The number of specific books in the library, 0 means it's out of stock")
    private int inventory;
}