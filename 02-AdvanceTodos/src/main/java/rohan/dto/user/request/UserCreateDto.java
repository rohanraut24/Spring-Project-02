package rohan.dto.user.request;

import lombok.*;
import rohan.model.Users;

@Builder
@AllArgsConstructor
@ToString(exclude = "password")
@Getter
@Setter
@NoArgsConstructor
public class UserCreateDto {
    private String username;
    private String password;
    private String email;
    private Users.UserRole userRole;
}
