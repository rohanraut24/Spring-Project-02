package rohan.mapper;

import org.springframework.stereotype.Component;
import rohan.dto.todo.reponse.TodoResponse;
import rohan.dto.todo.request.TodoRequest;
import rohan.dto.todo.request.UpdateTodoRequest;
import rohan.model.Todos;
;

@Component
public class TodoMapper {

    // Convert TodoRequest -> Todos
    public Todos todoRequestToTodo(TodoRequest todoRequest) {
        if (todoRequest == null) {
            return null;
        }

        Todos todo = new Todos();


        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setCompleted(todoRequest.isCompleted());

        return todo;
    }

    // Convert Todos -> TodoResponse
    public TodoResponse todoToTodoResponse(Todos todo) {
        if (todo == null) {
            return null;
        }

        TodoResponse response = new TodoResponse();

        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());

        // Mapping custom fields
        if (todo.getUser() != null) {
            response.setUserId(todo.getUser().getId());
        }

        response.setCreatedAt(todo.getCreated_at());
        response.setUpdatedAt(todo.getUpdated_at());

        return response;
    }

    // Update existing Todos from UpdateTodoRequest
    public void updateTodoFromUpdateRequest(UpdateTodoRequest updateRequest, Todos todo) {
        if (updateRequest == null || todo == null) {
            return;
        }

        /*
            ManyToOne(@JoinColumn(name =user_id) cascade=cascadeType.)
        */

        if (updateRequest.getTitle() != null) {
            todo.setTitle(updateRequest.getTitle());
        }

        if (updateRequest.getDescription() != null) {
            todo.setDescription(updateRequest.getDescription());
        }

        todo.setCompleted(updateRequest.getCompleted());

    }
}
