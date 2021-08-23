package by.hpk.task.container.exception;

public class BindingNotFoundException extends RuntimeException{
    public BindingNotFoundException(String message) {
        super(message);
    }

    public BindingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingNotFoundException(Throwable cause) {
        super(cause);
    }
}
