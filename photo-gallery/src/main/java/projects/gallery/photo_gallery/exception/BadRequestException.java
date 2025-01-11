package projects.gallery.photo_gallery.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class BadRequestException extends RuntimeException {
    private Map<String, String> errors;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}
