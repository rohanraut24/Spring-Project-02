package rohan.rest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rohan.dto.user.request.LoginRequest;
import rohan.dto.user.request.SignUpRequest;
import rohan.dto.ApiResponse;
import rohan.dto.ErrorResponse;
import rohan.dto.JwtResponse;
import rohan.security.CustomUserDetails;
import rohan.security.JwtUtil;
import rohan.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            if (userDetails instanceof CustomUserDetails) {
                CustomUserDetails customUser = (CustomUserDetails) userDetails;

                String jwt = jwtUtil.generateTokenWithUserInfo(
                        userDetails.getUsername(),
                        customUser.getId(),
                        customUser.getEmail(),
                        customUser.getUserRole().name()
                );

                return ResponseEntity.ok(new JwtResponse(
                        jwt,
                        customUser.getId(),
                        customUser.getUsername(),
                        customUser.getEmail(),
                        customUser.getUserRole().name()
                ));
            }

            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid username or password"));
        }
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        try {
//            ApiResponse response = userService.registerUser(signUpRequest);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(new ErrorResponse(e.getMessage()));
//        }
//    }
}