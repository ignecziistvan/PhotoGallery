package projects.gallery.photo_gallery.exception;

import java.util.Map;

public class BadRequestException extends RuntimeException {
    private Map<String, String> errors;
    public BadRequestException(String message) {
        super(message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
