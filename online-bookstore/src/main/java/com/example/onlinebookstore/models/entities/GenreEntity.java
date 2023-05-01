package com.example.onlinebookstore.models.entities;

import com.example.onlinebookstore.util.constants.Genre;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Genre genre;

    public GenreEntity() {
    }

    public GenreEntity(Genre genre) {
        this.genre = genre;
    }

    public static GenreEntity fromString(String genre) {
        return new GenreEntity(Genre.valueOf(genre));
    }
}