package exception;

public class SauvegardeException extends JavazicException {

    public SauvegardeException(String message) {
        super(message);
    }

    public SauvegardeException(String message, Throwable cause) {
        super(message, cause);
    }
}