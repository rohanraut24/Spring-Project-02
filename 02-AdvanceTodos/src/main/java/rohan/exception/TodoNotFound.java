package rohan.exception;



public class TodoNotFound extends RuntimeException {
    public TodoNotFound(String s){
        super(s);
    }
}
