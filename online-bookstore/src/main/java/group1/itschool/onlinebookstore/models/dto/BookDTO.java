package group1.itschool.onlinebookstore.models.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BookDTO implements Serializable {

    @NotNull
    @Size(min = 2, max = 100, message = "Should contain between 2 and 100 letters.")
    private String title;
    @NotNull
    private AuthorDTO author;
    @NotNull
    @Size(min = 2, max = 50, message = "Should contain between 2 and 50 letters.")
    private String publisher;
    @PastOrPresent
    private LocalDate publicationDate;
    @NotNull
    private String codeISBN;
    @NotNull
    private Genre genre;
    @NotNull
    private String synopsis;
    @NotNull
    private String coverDesign;
    @NotNull
    private int pageCount;
    @NotNull
    private String language;
    @NotNull
    private String format;
    @DecimalMin(value = "0.0", message = "Minimum price can't be 0.0.")
    private double price;
    @NotNull
    private String review;
    @NotBlank
    private String qrCode;
    @NotNull
    @Min(value = 0, message = "The number of specific books in the library, 0 means it's out of stock")
    private int inventory;

    public enum Genre {
        HORROR,
        MYSTERY,
        DRAMA,
        POETRY,
        ACTION,
        FICTION,
        SATIRE,
        HISTORICAL,
        PARODY
    }
}