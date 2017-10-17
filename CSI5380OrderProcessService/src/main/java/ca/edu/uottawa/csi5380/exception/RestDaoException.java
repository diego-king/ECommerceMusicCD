package ca.edu.uottawa.csi5380.exception;

public class RestDaoException extends RestException {

    public RestDaoException() {
        super();
    }

    public RestDaoException(String message) {
        super(message);
    }

    public RestDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
