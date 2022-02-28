package exceptions;

public class DAOException extends Exception {
    public DAOException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
