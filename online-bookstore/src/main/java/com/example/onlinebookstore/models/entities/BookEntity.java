package com.example.onlinebookstore.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    @Column(name = "isbn_code")
    private String codeISBN;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "books_genre", joinColumns = @JoinColumn(name = "books_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreEntity> genres = new HashSet<>();
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
    private String qrCode;
    @Column(name = "inventory")
    private int inventory;
}