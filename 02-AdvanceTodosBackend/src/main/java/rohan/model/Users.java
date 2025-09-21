package rohan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "password")
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    public enum UserRole{
        ADMIN,USER
    };
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , orphanRemoval = true , fetch =FetchType.EAGER)
    List<Todos> userTodos = new ArrayList<>();
}
