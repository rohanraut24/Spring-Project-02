package rohan.service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rohan.dto.ApiResponse;
import rohan.dto.todo.reponse.TodoStatsResponse;
import rohan.dto.todo.request.TodoRequest;
import rohan.dto.todo.reponse.TodoResponse;
import rohan.dto.todo.request.UpdateTodoRequest;
import rohan.exception.ResourceNotFoundException;
import rohan.exception.UserNotFound;
import rohan.mapper.TodoMapper;
import rohan.model.Todos;
import rohan.model.Users;
import rohan.repo.TodoRepo;
import rohan.repo.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepo todoRepository;
    private final UserRepo userRepository;
    private final ModelMapper modelMapper;


    @Autowired
    private TodoMapper todoMapper;

    private Users getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    @Override
    public TodoResponse createTodo(TodoRequest todoRequest, String username) {
        Users user = getCurrentUser(username);

        Todos todo = todoMapper.todoRequestToTodo(todoRequest);
        todo.setUser(user);

        Todos savedTodo = todoRepository.save(todo);
        return todoMapper.todoToTodoResponse(savedTodo);
    }

    @Override
    public List<TodoResponse> getAllTodosByUser(String username) {
        Users user = getCurrentUser(username);

        return todoRepository.findByUserId(user.getId()).stream()
                .map(todoMapper::todoToTodoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoResponse> getTodosByStatus(String username, boolean completed) {
        Users user = getCurrentUser(username);

        return todoRepository.findByUserIdAndCompleted(user.getId(), completed).stream()
                .map(todoMapper::todoToTodoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse getTodoById(Long id, String username) {
        Users user = getCurrentUser(username);

        Todos todo = todoRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    public TodoResponse updateTodo(Long id, UpdateTodoRequest updateRequest, String username) {
        Users user = getCurrentUser(username);

        Todos todo = todoRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todoMapper.updateTodoFromUpdateRequest(updateRequest, todo);
        Todos updatedTodo = todoRepository.save(todo);

        return todoMapper.todoToTodoResponse(updatedTodo);
    }

    @Override
    public ApiResponse deleteTodo(Long id, String username) {
        Users user = getCurrentUser(username);

        Todos todo = todoRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todoRepository.delete(todo);
        return new ApiResponse(true, "Todo deleted successfully!");
    }

    @Override
    public ApiResponse toggleTodoStatus(Long id, String username) {
        Users user = getCurrentUser(username);

        Todos todo = todoRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);

        String status = todo.isCompleted() ? "completed" : "pending";
        return new ApiResponse(true, "Todo marked as " + status + "!");
    }

    @Override
    public TodoStatsResponse getTodoStats(String username) {
        Users user = getCurrentUser(username);

        long totalTodos = todoRepository.countByUserIdAndCompleted(user.getId(), true) +
                todoRepository.countByUserIdAndCompleted(user.getId(), false);
        long completedTodos = todoRepository.countByUserIdAndCompleted(user.getId(), true);
        long pendingTodos = todoRepository.countByUserIdAndCompleted(user.getId(), false);

        return new TodoStatsResponse(totalTodos, completedTodos, pendingTodos);
    }

    @Override
    public List<TodoResponse> searchTodos(String username, String title) {
        Users user = getCurrentUser(username);

        return todoRepository.findByUserIdAndTitleContaining(user.getId(), title).stream()
                .map(todoMapper::todoToTodoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponse deleteAllCompletedTodos(String username) {
        Users user = getCurrentUser(username);

        List<Todos> completedTodos = todoRepository.findByUserIdAndCompleted(user.getId(), true);

        if (completedTodos.isEmpty()) {
            return new ApiResponse(true, "No completed todos to delete!");
        }

        todoRepository.deleteAll(completedTodos);
        return new ApiResponse(true, completedTodos.size() + " completed todos deleted successfully!");
    }
}
