package rohan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name ="user_id", nullable = false)
    private Users user;

    @Column
    private boolean completed;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false ,updatable = false)
//    @CreationTimestamp  //Don't need this because I already declared it in database
    private LocalDateTime createdAt;

    @Column(nullable = false)
//    @UpdateTimestamp //Don't need this because I already declared it in database
//    ADD COLUMN updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;  //In DB
    private LocalDateTime updatedAt;
}
