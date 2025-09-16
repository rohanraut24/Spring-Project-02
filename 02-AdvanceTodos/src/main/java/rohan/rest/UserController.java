package rohan.rest;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rohan.dto.request.CreateUserDto;
import rohan.dto.response.ResponseUserDto;
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
    public ResponseUserDto createUser(@RequestBody CreateUserDto createUserDto){
        return userService.create(createUserDto);
    }

}
