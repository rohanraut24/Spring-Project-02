package rohan.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import rohan.dto.user.request.SignUpRequest;
import rohan.dto.user.request.UpdateUserRequest;
import rohan.dto.user.response.UserResponse;
import rohan.model.Users;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "userTodos", ignore = true)
    @Mapping(target = "userRole", expression = "java(mapStringToUserRole(signUpRequest.getRole()))")
    Users signUpRequestToUser(SignUpRequest signUpRequest);

    UserResponse userToUserResponse(Users user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "userRole", ignore = true)
    @Mapping(target = "userTodos", ignore = true)
    void updateUserFromUpdateRequest(UpdateUserRequest updateRequest, @MappingTarget Users user);

    default Users.UserRole mapStringToUserRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return Users.UserRole.USER;
        }
        try {
            return Users.UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Users.UserRole.USER;
        }
    }
}