package dao;

/**
 * Created by ulysse on 06/11/2016.
 */
public class DAOException extends RuntimeException {
    public DAOException(String message) {
        super(message);
    }
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    public DAOException(Throwable cause) {
        super(cause);
    }
}
