package group1.itschool.onlinebookstore.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String productName;
    //@ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "user_id")
    //private UserEntity user;
}
