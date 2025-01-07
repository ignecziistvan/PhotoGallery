package projects.gallery.photo_gallery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new CustomExceptionResponse(e.getMessage(), e.getErrors())
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

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<CustomExceptionResponse> handleGeneralException(GeneralException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new CustomExceptionResponse(e.getMessage(), null)
        );
    }
}