package projects.gallery.photo_gallery.exception;

import java.util.Map;

public class NotFoundException extends RuntimeException {
    private Map<String, String> errors;
    public NotFoundException(String message) {
        super(message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
