package com.example.onlinebookstore.models.entities;

import com.example.onlinebookstore.util.constants.Genre;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "genres")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Genre genre;

    @ManyToMany(mappedBy = "genres")
    private Set<BookEntity> books = new HashSet<>();

    public GenreEntity() {
    }

    public GenreEntity(Genre genre) {
        this.genre = genre;
    }

    public static GenreEntity fromString(String genre) {
        return new GenreEntity(Genre.valueOf(genre));
    }

    public String getName() {
        return genre.name();
    }

    public void setName(String name) {
        this.genre = Genre.valueOf(name);
    }
}