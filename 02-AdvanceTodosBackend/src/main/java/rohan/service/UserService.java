package rohan.service;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import rohan.dto.ApiResponse;
import rohan.dto.todo.reponse.TodoResponse;
import rohan.dto.todo.request.TodoRequest;
import rohan.dto.todo.request.UpdateTodoRequest;
import rohan.dto.user.request.ChangePasswordRequest;
import rohan.dto.user.request.SignUpRequest;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UpdateUserRequest;
import rohan.dto.user.response.UserResponse;
import rohan.model.Users;

import java.util.List;

public interface UserService {
    ApiResponse registerUser(SignUpRequest signUpRequest);
    UserResponse getUserById(Long id);
    UserResponse getCurrentUser(String username);
    List<UserResponse> getAllUsers();
    ApiResponse updateUser(Long id, UpdateUserRequest updateRequest, String currentUsername);
    ApiResponse changePassword(String username, ChangePasswordRequest changePasswordRequest);
    ApiResponse deleteUser(Long id, String currentUsername);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

//    List<Users> getList();
//    UserResponse create(UserCreateDto userCreateDto);
//    TodoResponse createTodo(Long id, TodoRequest todoRequest);
//    ResponseEntity<String> todoUpdate(Long id, UpdateTodoRequest updateTodoRequest);
//    UserResponse update(Long id, UpdateUserRequest updateUserRequest);
}
