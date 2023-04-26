package group1.itschool.onlinebookstore.repositories;

import group1.itschool.onlinebookstore.models.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * This interface is a component repository from Spring Framework, the AuthorRepository interface extends the JpaRepository
 * interface, this will allow us to use the standard CRUD operations for the Book entity class. BookEntity is the table from the database.
 * Long is the primary key.
 * This comment will be erased once the pull requests is approved.
 */
@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {

    @Query
    AuthorEntity findByName(String name);
}