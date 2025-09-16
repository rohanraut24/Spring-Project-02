package myCode.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.mapstruct.ap.internal.model.GeneratedType;

@Data
@ToString(exclude = "password")
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
    private Integer username;
    private Integer password;
}
