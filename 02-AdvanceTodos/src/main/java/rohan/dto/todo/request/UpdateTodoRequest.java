package rohan.dto.todo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UpdateTodoRequest {
    private String title;
    private String description;
    private boolean completed;
}
