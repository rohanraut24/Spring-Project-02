package rohan.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rohan.dto.*;
import rohan.dto.user.request.ChangePasswordRequest;
import rohan.dto.user.request.SignUpRequest;
import rohan.dto.user.request.UpdateUserRequest;
import rohan.exception.ResourceNotFoundException;
import rohan.exception.BadRequestException;
import rohan.mapper.UserMapper;
import rohan.model.Users;
import rohan.repo.UserRepo;
import rohan.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponse registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email is already in use!");
        }

        Users user = userMapper.signUpRequestToUser(signUpRequest);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        return new ApiResponse(true, "User registered successfully!");
    }

    @Override
    public ApiResponse registerUser(SignUpRequest signUpRequest) {
        return null;
    }

    @Override
    public UserResponse getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponse getCurrentUser(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.userToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponse updateUser(Long id, UpdateUserRequest updateRequest, String currentUsername) {
        return null;
    }

    @Override
    public ApiResponse changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        return null;
    }

    @Override
    public ApiResponse updateUser(Long id, UpdateUserRequest updateRequest, String currentUsername) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        Users currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        if (!user.getId().equals(currentUser.getId()) &&
                !currentUser.getUserRole().equals(Users.UserRole.ADMIN)) {
            throw new BadRequestException("You can only update your own profile!");
        }

        if (updateRequest.getUsername() != null && !updateRequest.getUsername().trim().isEmpty()) {
            if (!user.getUsername().equals(updateRequest.getUsername()) &&
                    userRepository.existsByUsername(updateRequest.getUsername())) {
                throw new BadRequestException("Username is already taken!");
            }
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().trim().isEmpty()) {
            if (!user.getEmail().equals(updateRequest.getEmail()) &&
                    userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new BadRequestException("Email is already in use!");
            }
        }

        userMapper.updateUserFromUpdateRequest(updateRequest, user);
        userRepository.save(user);

        return new ApiResponse(true, "User updated successfully!");
    }

    @Override
    public ApiResponse changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect!");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);

        return new ApiResponse(true, "Password changed successfully!");
    }

    @Override
    public ApiResponse deleteUser(Long id, String currentUsername) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        Users currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        if (!user.getId().equals(currentUser.getId()) &&
                !currentUser.getUserRole().equals(Users.UserRole.ADMIN)) {
            throw new BadRequestException("You can only delete your own account!");
        }

        userRepository.delete(user);
        return new ApiResponse(true, "User deleted successfully!");
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}