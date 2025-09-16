package rohan.exception;


public class UserNotFound extends RuntimeException{
    public UserNotFound(String str) {
        super(str);
    }
}
