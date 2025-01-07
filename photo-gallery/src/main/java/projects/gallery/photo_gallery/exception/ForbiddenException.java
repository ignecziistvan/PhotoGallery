package projects.gallery.photo_gallery.exception;

import java.util.Map;

public class ForbiddenException extends RuntimeException {
    private Map<String, String> errors;
    public ForbiddenException(String message) {
        super(message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
