package rohan.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rohan.dto.todo.reponse.ResponseTodoDto;
import rohan.dto.todo.request.TodoCreateDto;
import rohan.dto.todo.request.UpdateTodoDto;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UserUpdateDto;
import rohan.dto.user.response.ResponseUserDto;
import rohan.exception.TodoNotFound;
import rohan.exception.UserNotFound;
import rohan.model.Todos;
import rohan.model.Users;
import rohan.repo.TodoRepo;
import rohan.repo.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final PasswordEncoder encoder;
    private final UserRepo userRepo;
    private final TodoRepo todoRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<Users> getList() {
        return userRepo.findAll();
    }

    @Override
    public List<ResponseTodoDto> getTodos(Long id) {
        Users user = userRepo.findById(id).orElseThrow(()->new UserNotFound("User not found"));
        return user.getUserTodos()
                .stream()
                .map(todos->modelMapper.map(todos,ResponseTodoDto.class))
                .toList();
    }

    @Override
    public ResponseUserDto create(UserCreateDto userCreateDto) {
        Users user = modelMapper.map(userCreateDto,Users.class);
        user.setPassword(encoder.encode(userCreateDto.getPassword()));
        return modelMapper.map(userRepo.save(user),ResponseUserDto.class);
    }

    @Override
    public ResponseTodoDto createTodo(Long id, TodoCreateDto todoCreateDto) {
        Users user = userRepo.findById(id).orElseThrow(()->new UserNotFound("User not found"));

        Todos newTodo = modelMapper.map(todoCreateDto,Todos.class);
        newTodo.setUser(user);

        user.getUserTodos().add(newTodo);
        userRepo.save(user);

        return modelMapper.map(newTodo,ResponseTodoDto.class);
    }

    @Override
    public ResponseEntity<String> todoUpdate(Long id, UpdateTodoDto updateTodoDto) {
        Todos todo = todoRepo.findById(id).orElseThrow(()->new TodoNotFound("Todo not found with this id"));
        modelMapper.map(updateTodoDto,todo);
        todoRepo.save(todo);
        return  ResponseEntity.ok("Updated successfully");
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
