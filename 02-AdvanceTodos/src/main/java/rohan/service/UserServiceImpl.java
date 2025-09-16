package rohan.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rohan.dto.request.CreateUserDto;
import rohan.dto.response.ResponseUserDto;
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
    public ResponseUserDto create(CreateUserDto createUserDto) {
        Users user = modelMapper.map(createUserDto,Users.class);
        user.setPassword(encoder.encode(createUserDto.getPassword()));
        return modelMapper.map(userRepo.save(user),ResponseUserDto.class);
    }
}
