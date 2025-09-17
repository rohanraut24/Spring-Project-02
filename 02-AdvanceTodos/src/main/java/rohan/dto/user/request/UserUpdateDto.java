package rohan.dto.user.request;

import lombok.*;
import rohan.model.Users;

@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
    private String username;
    private String password;
    private String email;
    private Users.UserRole userRole;
}
