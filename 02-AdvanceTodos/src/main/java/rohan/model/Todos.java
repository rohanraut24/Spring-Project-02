package rohan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore  //solve issue of cause infinite nesting problem
    private Users user;

    @Column
    private boolean completed;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false ,updatable = false ,insertable = false)
//    @CreationTimestamp  //Don't need this because I already declared it in database
    private LocalDateTime created_at;

    @Column(name="updated_at",nullable = false ,insertable = false)
//    @UpdateTimestamp //Don't need this because I already declared it in database
//    ADD COLUMN updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;  //In DB
    //Updated through db is not working properly
    @UpdateTimestamp
    private LocalDateTime updated_at;
}
