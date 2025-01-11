package projects.gallery.photo_gallery.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class UnauthorizedException extends RuntimeException {
    private Map<String, String> errors;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}
