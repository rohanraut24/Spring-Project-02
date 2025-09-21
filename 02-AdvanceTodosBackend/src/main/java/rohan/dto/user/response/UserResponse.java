package rohan.dto.user.response;
import lombok.*;
import rohan.model.Users;

@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Users.UserRole userRole;
}
