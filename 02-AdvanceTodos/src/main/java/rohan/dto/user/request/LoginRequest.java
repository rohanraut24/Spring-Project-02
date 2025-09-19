package rohan.dto.user.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import rohan.model.Users;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class LoginUserDto {
    @NotBlank
    private String username;

    @Size(min = 4, message = "password should be min 4 digit or alphabet")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password should contain only letters and digits")
    private String password;

}