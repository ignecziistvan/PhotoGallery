package projects.gallery.photo_gallery.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ForbiddenException extends RuntimeException {
    private Map<String, String> errors;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}
