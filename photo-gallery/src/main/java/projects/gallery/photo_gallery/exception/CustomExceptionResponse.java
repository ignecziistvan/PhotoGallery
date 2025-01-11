package projects.gallery.photo_gallery.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class CustomExceptionResponse {
    private final String error;
    private final Map<String, String> errors;
}
