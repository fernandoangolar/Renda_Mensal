package ao.com.techAngolar.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( (error) -> {
            String fieldName = ( (FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(ResourceEntityNotFoundException.class)
    public ResponseEntity<StardarError> entityNotFound(ResourceEntityNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        StardarError error = new StardarError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entity not found");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status)
                .body(error);
    }

    @ExceptionHandler(ResourceInativoExcepton.class)
    public ResponseEntity<StardarError> entityInative(ResourceInativoExcepton ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StardarError error = new StardarError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entity Inativa");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status)
                .body(error);
    }

    @ExceptionHandler(ResourceJaExistenteException.class)
    public ResponseEntity<StardarError> entityInative(ResourceJaExistenteException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StardarError error = new StardarError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entity Existente");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status)
                .body(error);
    }
}
