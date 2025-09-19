package rohan.service;

import org.springframework.http.ResponseEntity;
import rohan.dto.todo.reponse.TodoResponse;
import rohan.dto.todo.request.TodoRequest;
import rohan.dto.todo.request.UpdateTodoRequest;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UpdateUserRequest;
import rohan.dto.user.response.UserResponse;
import rohan.model.Users;

import java.util.List;

public interface UserService {
    List<Users> getList();
    List<TodoResponse> getTodos(Long id);
    UserResponse create(UserCreateDto userCreateDto);
    TodoResponse createTodo(Long id, TodoRequest todoRequest);
    ResponseEntity<String> todoUpdate(Long id, UpdateTodoRequest updateTodoRequest);
    UserResponse update(Long id, UpdateUserRequest updateUserRequest);
}
