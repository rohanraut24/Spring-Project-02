package rohan.dto;

import lombok.Data;


@Data
public class ErrorResponse {
    private String message;
    private int status;
    private long timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}