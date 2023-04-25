package group1.itschool.onlinebookstore.repositories;

import group1.itschool.onlinebookstore.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is a component repository from Spring Framework,the UserRepository interface extends the JpaRepository
 * interface, this will allow us to use the standard CRUD operations for the User entity class. UserEntity is the table from the database.
 * Long is the primary key.
 * This comment will be erased once the pull requests is approved.
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}