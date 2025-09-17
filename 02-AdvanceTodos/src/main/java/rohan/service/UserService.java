package rohan.service;

import org.springframework.http.ResponseEntity;
import rohan.dto.todo.reponse.ResponseTodoDto;
import rohan.dto.todo.request.TodoCreateDto;
import rohan.dto.todo.request.UpdateTodoDto;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UserUpdateDto;
import rohan.dto.user.response.ResponseUserDto;
import rohan.model.Users;

import java.util.List;

public interface UserService {
    List<Users> getList();
    List<ResponseTodoDto> getTodos(Long id);
    ResponseUserDto create(UserCreateDto userCreateDto);
    ResponseTodoDto createTodo(Long id, TodoCreateDto todoCreateDto);
    ResponseEntity<String> todoUpdate(Long id, UpdateTodoDto updateTodoDto);
    ResponseUserDto update(Long id, UserUpdateDto userUpdateDto);
}
