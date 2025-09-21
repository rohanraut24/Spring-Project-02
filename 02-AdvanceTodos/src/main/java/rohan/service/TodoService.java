package rohan.service;

import rohan.dto.ApiResponse;
import rohan.dto.todo.reponse.TodoResponse;
import rohan.dto.todo.reponse.TodoStatsResponse;
import rohan.dto.todo.request.TodoRequest;
import rohan.dto.todo.request.UpdateTodoRequest;

import java.util.List;

public interface TodoService {
    TodoResponse createTodo(TodoRequest todoRequest, String username);
    List<TodoResponse> getAllTodosByUser(String username);
    List<TodoResponse> getTodosByStatus(String username, boolean completed);
    TodoResponse getTodoById(Long id, String username);
    TodoResponse updateTodo(Long id, UpdateTodoRequest updateRequest, String username);
    ApiResponse deleteTodo(Long id, String username);
    ApiResponse toggleTodoStatus(Long id, String username);
    TodoStatsResponse getTodoStats(String username);
    List<TodoResponse> searchTodos(String username, String title);
    ApiResponse deleteAllCompletedTodos(String username);
}
