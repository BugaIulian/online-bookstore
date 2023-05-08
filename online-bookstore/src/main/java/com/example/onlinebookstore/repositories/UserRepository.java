package com.example.onlinebookstore.repositories;

import com.example.onlinebookstore.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query
    UserEntity findByUsername(String username);

    @Query
    UserEntity findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM users")
    Long countUsers();
}