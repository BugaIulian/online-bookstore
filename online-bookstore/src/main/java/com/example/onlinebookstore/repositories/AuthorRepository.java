package com.example.onlinebookstore.repositories;

import com.example.onlinebookstore.models.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    @Query
    AuthorEntity findByName(String name);
}