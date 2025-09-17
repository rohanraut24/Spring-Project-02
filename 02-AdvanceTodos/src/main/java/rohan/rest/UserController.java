package rohan.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UserUpdateDto;
import rohan.dto.user.response.ResponseUserDto;
import rohan.model.Users;
import rohan.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<Users> getList(){
        return userService.getList();
    }

    @PostMapping("/users")
    public ResponseUserDto createUser(@RequestBody UserCreateDto userCreateDto){
        return userService.create(userCreateDto);
    }

    @PatchMapping("/users/{id}")
    public ResponseUserDto UpdateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto){
        return userService.update(id, userUpdateDto);
    }

}
