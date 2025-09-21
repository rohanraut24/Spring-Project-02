package rohan.dto.user.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
public class LoginRequest {
    @NotBlank(message = "Username is required")
    private String username;


    private String password;
}