package rohan.rest.userRest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rohan.dto.todo.reponse.ResponseTodoDto;
import rohan.dto.todo.request.TodoCreateDto;
import rohan.dto.todo.request.UpdateTodoDto;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UserUpdateDto;
import rohan.dto.user.response.ResponseUserDto;
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
    public ResponseUserDto createUser(@RequestBody UserCreateDto userCreateDto){
        return userService.create(userCreateDto);
    }

    @PostMapping("/todos/{id}")
    public ResponseTodoDto createTodo(@PathVariable Long id, @RequestBody TodoCreateDto todoCreateDto){
        return userService.createTodo(id,todoCreateDto);
    }
    @PutMapping("/todos/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id, @RequestBody UpdateTodoDto updateTodoDto){
        return  userService.todoUpdate(id, updateTodoDto);
    }

    @PatchMapping("/{id}")
    public ResponseUserDto UpdateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto){
        return userService.update(id, userUpdateDto);
    }
}
