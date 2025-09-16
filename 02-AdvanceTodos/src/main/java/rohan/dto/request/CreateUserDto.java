package rohan.dto.request;

import jakarta.persistence.*;
import lombok.*;
import rohan.model.Users;

@Builder
@AllArgsConstructor
@ToString(exclude = "password")
@Getter
@Setter
@NoArgsConstructor
public class CreateUserDto {
    private String username;
    private String password;
    private String email;
    private Users.UserRole userRole;
}
