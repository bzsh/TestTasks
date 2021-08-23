package by.hpk.task.container.exception;

public class TooManyConstructorsException extends RuntimeException{
    public TooManyConstructorsException(String message) {
        super(message);
    }

    public TooManyConstructorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyConstructorsException(Throwable cause) {
        super(cause);
    }
}
