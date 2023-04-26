package group1.itschool.onlinebookstore.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "interests")
    private String interests;
    @Column(name = "accountCreationDate")
    private LocalDate accountCreationDate;
}