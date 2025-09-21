package rohan.rest.todoRest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rohan.dto.todo.request.TodoRequest;
import rohan.dto.todo.request.UpdateTodoRequest;
import rohan.dto.ApiResponse;
import rohan.dto.todo.reponse.TodoResponse;
import rohan.dto.todo.reponse.TodoStatsResponse;
import rohan.service.TodoService;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @Valid @RequestBody TodoRequest todoRequest,
            Authentication authentication) {
        TodoResponse todoResponse = todoService.createTodo(todoRequest, authentication.getName());
        return new ResponseEntity<>(todoResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos(Authentication authentication) {
        List<TodoResponse> todos = todoService.getAllTodosByUser(authentication.getName());
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/status/{completed}")
    public ResponseEntity<List<TodoResponse>> getTodosByStatus(
            @PathVariable boolean completed,
            Authentication authentication) {
        List<TodoResponse> todos = todoService.getTodosByStatus(authentication.getName(), completed);
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @PathVariable Long id,
            Authentication authentication) {
        TodoResponse todo = todoService.getTodoById(id, authentication.getName());
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTodoRequest updateRequest,
            Authentication authentication) {
        TodoResponse todoResponse = todoService.updateTodo(id, updateRequest, authentication.getName());
        return ResponseEntity.ok(todoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTodo(
            @PathVariable Long id,
            Authentication authentication) {
        ApiResponse response = todoService.deleteTodo(id, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse> toggleTodoStatus(
            @PathVariable Long id,
            Authentication authentication) {
        ApiResponse response = todoService.toggleTodoStatus(id, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<TodoStatsResponse> getTodoStats(Authentication authentication) {
        TodoStatsResponse stats = todoService.getTodoStats(authentication.getName());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TodoResponse>> searchTodos(
            @RequestParam String title,
            Authentication authentication) {
        List<TodoResponse> todos = todoService.searchTodos(authentication.getName(), title);
        return ResponseEntity.ok(todos);
    }

    @DeleteMapping("/completed")
    public ResponseEntity<ApiResponse> deleteAllCompletedTodos(Authentication authentication) {
        ApiResponse response = todoService.deleteAllCompletedTodos(authentication.getName());
        return ResponseEntity.ok(response);
    }
}
