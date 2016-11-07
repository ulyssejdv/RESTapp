package dao;

/**
 * Created by ulysse on 06/11/2016.
 */
public class DAOConfigurationException extends RuntimeException {
    public DAOConfigurationException(String message) {
        super(message);
    }
    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }
}
