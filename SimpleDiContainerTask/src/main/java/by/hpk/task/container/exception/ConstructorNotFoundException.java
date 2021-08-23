package by.hpk.task.container.exception;

public class ConstructorNotFoundException extends RuntimeException{
    public ConstructorNotFoundException(String message) {
        super(message);
    }

    public ConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstructorNotFoundException(Throwable cause) {
        super(cause);
    }
}
