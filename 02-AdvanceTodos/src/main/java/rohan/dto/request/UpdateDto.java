package rohan.dto.request;

import jakarta.validation.Valid;
import lombok.*;
import rohan.model.Users;

@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class UpdateDto {
    private String username;
    private String password;
    private String email;
    private Users.UserRole userRole;
}
