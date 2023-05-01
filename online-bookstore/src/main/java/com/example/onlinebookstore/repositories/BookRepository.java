package com.example.onlinebookstore.repositories;

import com.example.onlinebookstore.models.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is a component repository from Spring Framework,the BookRepository interface extends the JpaRepository
 * interface, this will allow us to use the standard CRUD operations for the Book entity class. BookEntity is the table from the database.
 * Long is the primary key.
 * This comment will be erased once the pull requests is approved.
 */

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {
}