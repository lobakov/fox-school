package ua.com.foxminded.sqljdbcschool.exception;

public class InvalidInputStreamException extends RuntimeException {

    private static final long serialVersionUID = 100L;

    public InvalidInputStreamException(String msg) {
        super(msg);
    }
}
