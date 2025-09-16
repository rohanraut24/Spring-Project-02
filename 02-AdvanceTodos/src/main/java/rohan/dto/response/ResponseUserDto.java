package rohan.dto.response;
import lombok.*;
import rohan.model.Users;

@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ResponseUserDto {
    private Long id;
    private String username;
    private String email;
    private Users.UserRole userRole;
}
