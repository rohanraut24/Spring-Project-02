package rohan.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UserUpdateDto;
import rohan.dto.user.response.ResponseUserDto;
import rohan.exception.UserNotFound;
import rohan.model.Users;
import rohan.repo.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final PasswordEncoder encoder;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<Users> getList() {
        return userRepo.findAll();
    }

    @Override
    public ResponseUserDto create(UserCreateDto userCreateDto) {
        Users user = modelMapper.map(userCreateDto,Users.class);
        user.setPassword(encoder.encode(userCreateDto.getPassword()));
        return modelMapper.map(userRepo.save(user),ResponseUserDto.class);
    }

    @Override
    public ResponseUserDto update(Long id, UserUpdateDto userUpdateDto) {
        Users existingUser = userRepo.findById(id).orElseThrow(() -> new UserNotFound("User not found for id " + id));

        if (userUpdateDto.getUsername()!= null) {
            existingUser.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getEmail() != null) {
            existingUser.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null) {
            existingUser.setPassword(encoder.encode(userUpdateDto.getPassword()));
        }

        Users savedUser = userRepo.save(existingUser);

        return modelMapper.map(savedUser, ResponseUserDto.class);
    }
}
