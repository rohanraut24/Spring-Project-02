package rohan.rest.userRest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rohan.dto.todo.reponse.TodoResponse;
import rohan.dto.todo.request.TodoRequest;
import rohan.dto.todo.request.UpdateTodoRequest;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UpdateUserRequest;
import rohan.dto.user.response.UserResponse;
import rohan.model.Users;
import rohan.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<Users> getList(){
        return userService.getList();
    }

    @GetMapping("/todos/{id}")
    public List<Users> getTos(@PathVariable Long id) {
        return userService.getList();
    }

    @PostMapping()
    public UserResponse createUser(@RequestBody UserCreateDto userCreateDto){
        return userService.create(userCreateDto);
    }

    @PostMapping("/todos/{id}")
    public TodoResponse createTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest){
        return userService.createTodo(id, todoRequest);
    }
    @PutMapping("/todos/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequest updateTodoRequest){
        return  userService.todoUpdate(id, updateTodoRequest);
    }

    @PatchMapping("/{id}")  
    public UserResponse UpdateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest){
        return userService.update(id, updateUserRequest);
    }
}
