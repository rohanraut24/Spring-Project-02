package rohan.service;

import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UserUpdateDto;
import rohan.dto.user.response.ResponseUserDto;
import rohan.model.Users;

import java.util.List;

public interface UserService {
    List<Users> getList();
    ResponseUserDto create(UserCreateDto userCreateDto);

    ResponseUserDto update(Long id, UserUpdateDto userUpdateDto);
}
