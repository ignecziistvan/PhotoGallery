package projects.gallery.photo_gallery.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public CustomExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new CustomExceptionResponse(e.getMessage(), null)
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomExceptionResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new CustomExceptionResponse(e.getMessage(), e.getErrors())
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomExceptionResponse> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new CustomExceptionResponse(e.getMessage(), e.getErrors())
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomExceptionResponse> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new CustomExceptionResponse(e.getMessage(), e.getErrors())
        );
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<CustomExceptionResponse> handleGeneralException(GeneralException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new CustomExceptionResponse(e.getMessage(), null)
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleFormValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        String message = messageSource.getMessage(
                "form-validation-error",
                null,
                "Error validating data",
                LocaleContextHolder.getLocale()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new CustomExceptionResponse(message, errors)
        );
    }
}