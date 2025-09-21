package rohan.dto.todo.reponse;

import lombok.Data;

@Data
public class TodoStatsResponse {
    private long totalTodos;
    private long completedTodos;
    private long pendingTodos;

    public TodoStatsResponse(long totalTodos, long completedTodos, long pendingTodos) {
        this.totalTodos = totalTodos;
        this.completedTodos = completedTodos;
        this.pendingTodos = pendingTodos;
    }
}