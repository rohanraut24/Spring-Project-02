package rohan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private long timestamp;

    public ErrorResponse(String message) {
        this.message = message;
//        this.timestamp = System.currentTimeMillis();
    }

//    public ErrorResponse(String message, long timestamp, int status) {
//        this.message = message;
//        this.timestamp = System.currentTimeMillis();
//        this.status = 1000;//check
//    }
}