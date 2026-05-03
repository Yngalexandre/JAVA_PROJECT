package exception;

public class JavazicException extends RuntimeException {

    public JavazicException(String message) {
        super(message);
    }

    public JavazicException(String message, Throwable cause) {
        super(message, cause);
    }
}