package exceptions;

/**
 * DAO Exception class
 */
public class DAOException extends Exception {
    /**
     * DAO Exception object constructor
     * @param message error message
     * @param throwable throwable
     */
    public DAOException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
