package exceptions;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
