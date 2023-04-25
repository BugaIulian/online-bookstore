package group1.itschool.onlinebookstore.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookEntity {

    /** Some fields may be erased later in the process of building the app for a better query performance, we will decide later which ones.*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    @Column(name = "isbn_code")
    private int codeISBN;
    @Column(name = "genre")
    private String genre;
    @Column(name = "synopsis")
    private String synopsis;
    @Column(name = "cover_design")
    private String coverDesign;
    @Column(name = "page_count")
    private int pageCount;
    @Column(name = "language")
    private String language;
    @Column(name = "format")
    private String format;
    @Column(name = "price")
    private double price;
    @Column(name = "review")
    private String review;
    @Column(name = "qr_code")
    private String QRCode;
    @Column(name = "inventory")
    private int inventory;
}