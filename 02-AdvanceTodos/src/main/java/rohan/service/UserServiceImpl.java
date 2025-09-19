package rohan.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rohan.dto.todo.reponse.TodoResponse;
import rohan.dto.todo.request.TodoRequest;
import rohan.dto.todo.request.UpdateTodoRequest;
import rohan.dto.user.request.UserCreateDto;
import rohan.dto.user.request.UpdateUserRequest;
import rohan.dto.user.response.UserResponse;
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
    public List<TodoResponse> getTodos(Long id) {
        Users user = userRepo.findById(id).orElseThrow(()->new UserNotFound("User not found"));
        return user.getUserTodos()
                .stream()
                .map(todos->modelMapper.map(todos, TodoResponse.class))
                .toList();
    }

    @Override
    public UserResponse create(UserCreateDto userCreateDto) {
        Users user = modelMapper.map(userCreateDto,Users.class);
        user.setPassword(encoder.encode(userCreateDto.getPassword()));
        return modelMapper.map(userRepo.save(user), UserResponse.class);
    }

    @Override
    public TodoResponse createTodo(Long id, TodoRequest todoRequest) {
        Users user = userRepo.findById(id).orElseThrow(()->new UserNotFound("User not found"));

        Todos newTodo = modelMapper.map(todoRequest,Todos.class);
        newTodo.setUser(user);

        user.getUserTodos().add(newTodo);
        userRepo.save(user);

        return modelMapper.map(newTodo, TodoResponse.class);
    }

    @Override
    public ResponseEntity<String> todoUpdate(Long id, UpdateTodoRequest updateTodoRequest) {
        Todos todo = todoRepo.findById(id).orElseThrow(()->new TodoNotFound("Todo not found with this id"));
        modelMapper.map(updateTodoRequest,todo);
        todoRepo.save(todo);
        return  ResponseEntity.ok("Updated successfully");
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest updateUserRequest) {
        Users existingUser = userRepo.findById(id).orElseThrow(() -> new UserNotFound("User not found for id " + id));

        if (updateUserRequest.getUsername()!= null) {
            existingUser.setUsername(updateUserRequest.getUsername());
        }
        if (updateUserRequest.getEmail() != null) {
            existingUser.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getPassword() != null) {
            existingUser.setPassword(encoder.encode(updateUserRequest.getPassword()));
        }

        Users savedUser = userRepo.save(existingUser);

        return modelMapper.map(savedUser, UserResponse.class);
    }
}
