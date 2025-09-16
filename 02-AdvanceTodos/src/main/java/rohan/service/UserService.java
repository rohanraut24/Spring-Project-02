package rohan.service;

import rohan.dto.request.CreateUserDto;
import rohan.dto.response.ResponseUserDto;
import rohan.model.Users;

import java.util.List;

public interface UserService {
    List<Users> getList();
    ResponseUserDto create(CreateUserDto createUserDto);
}
