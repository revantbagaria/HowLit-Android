package hu.ait.howlit.exception.exceptions;



public class unexpectedApiError extends YelpError{
    public unexpectedApiError(int responseCode, String message) {
        this(responseCode, message, null, null);
    }

    public unexpectedApiError(int responseCode, String message, String code, String description) {
        super(responseCode, message, code, description);
    }
}
