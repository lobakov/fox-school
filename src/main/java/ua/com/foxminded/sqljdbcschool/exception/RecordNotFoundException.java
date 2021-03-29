package ua.com.foxminded.sqljdbcschool.exception;

public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 100L;

    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
