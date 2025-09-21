package rohan.mapper;
import org.springframework.stereotype.Component;
import rohan.dto.user.request.SignUpRequest;
import rohan.dto.user.request.UpdateUserRequest;
import rohan.dto.user.response.UserResponse;
import rohan.model.Users;

@Component
public class UserMapper {

    public Users signUpRequestToUser(SignUpRequest signUpRequest) {
        if (signUpRequest == null) {
            return null;
        }

        Users user = new Users();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());

        // Set role
        if (signUpRequest.getRole() != null && !signUpRequest.getRole().trim().isEmpty()) {
            try {
                user.setUserRole(Users.UserRole.valueOf(signUpRequest.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setUserRole(Users.UserRole.USER);
            }
        } else {
            user.setUserRole(Users.UserRole.USER);
        }

        return user;
    }

    public UserResponse userToUserResponse(Users user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setUserRole(user.getUserRole());

        return response;
    }

    public void updateUserFromUpdateRequest(UpdateUserRequest updateRequest, Users user) {
        if (updateRequest == null || user == null) {
            return;
        }

        if (updateRequest.getUsername() != null && !updateRequest.getUsername().trim().isEmpty()) {
            user.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().trim().isEmpty()) {
            user.setEmail(updateRequest.getEmail());
        }
    }
}