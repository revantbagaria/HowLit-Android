package hu.ait.howlit.exception.exceptions;

import java.io.IOException;


public class YelpError extends IOException {
    private String message;
    private String code;
    private String description;
    private int responseCode;

    public String getCode() {
        return code;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public YelpError(int responseCode, String message, String code, String description) {
        this.responseCode = responseCode;
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
