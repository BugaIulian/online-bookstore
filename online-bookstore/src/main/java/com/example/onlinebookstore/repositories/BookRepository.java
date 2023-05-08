package com.example.onlinebookstore.repositories;

import com.example.onlinebookstore.models.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query
    BookEntity findBookByTitle(String title);
}