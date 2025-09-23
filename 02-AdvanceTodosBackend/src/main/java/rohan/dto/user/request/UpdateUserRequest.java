package rohan.dto.user.request;

import jakarta.validation.constraints.Email;
import lombok.*;
import rohan.model.Users;

@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
    private String username;

    @Email(message="Email should be valid")
    private String email;
}
