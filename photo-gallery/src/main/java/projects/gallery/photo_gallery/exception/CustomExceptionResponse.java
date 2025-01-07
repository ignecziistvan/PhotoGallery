package projects.gallery.photo_gallery.exception;

import java.util.Map;

public class CustomExceptionResponse {
    private final String error;
    private final Map<String, String> errors;

    public CustomExceptionResponse(String error, Map<String, String> errors) {
        this.error = error;
        this.errors = errors;
    }

    public String getError() {
        return error;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
