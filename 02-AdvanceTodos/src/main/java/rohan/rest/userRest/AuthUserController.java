//package rohan.rest.userRest;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import rohan.model.Users;
//import rohan.repo.UserRepo;
//import rohan.service.UserService;
//
//@RestController
//@RequiredArgsConstructor
//public class AuthUserController {
//    private final UserService userService;
//    private final UserRepo userRepo;
//
//    @PutMapping("/register")
//    public ResponseEntity<Users> register(@RequestBody Users user) {
//        return ResponseEntity.ok(userService.registerUser(user));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Users loginRequest) {
//        try {
//            Users user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
//            return ResponseEntity.ok("Login successful! Role: " + user.getUserRole());
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        }
//    }
//}
