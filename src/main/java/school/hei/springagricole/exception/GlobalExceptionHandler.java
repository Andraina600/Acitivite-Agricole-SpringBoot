package school.hei.springagricole.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler global pour convertir nos exceptions métier en réponses HTTP propres.
 *
 * IMPORTANT : Sans ce handler, Spring Boot peut parfois retourner des codes
 * inattendus (comme 418) au lieu des 400/404 attendus.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFound(NotFoundException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntime(RuntimeException ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Internal server error: " + ex.getMessage());
  }
}
