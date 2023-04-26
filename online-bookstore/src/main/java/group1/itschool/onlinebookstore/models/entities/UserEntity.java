package group1.itschool.onlinebookstore.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "users")
public class UserEntity {

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
    @Column(name = "accountCreatinDate")
    private String accountCreationDate;
}