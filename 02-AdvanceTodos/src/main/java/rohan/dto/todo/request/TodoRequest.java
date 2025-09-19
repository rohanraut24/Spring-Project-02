package rohan.dto.todo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoCreateDto {
    private String title;
    private String description;
    private boolean completed;
}
