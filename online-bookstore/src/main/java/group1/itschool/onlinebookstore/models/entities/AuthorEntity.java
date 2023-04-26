package group1.itschool.onlinebookstore.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "authors", uniqueConstraints = {@UniqueConstraint(columnNames = "author_name")})
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author_name")
    private String name;
    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<BookEntity> books;
}