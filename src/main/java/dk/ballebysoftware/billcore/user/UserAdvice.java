package dk.ballebysoftware.billcore.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dk.ballebysoftware.billcore.exceptions.DuplicateUserException;
import dk.ballebysoftware.billcore.exceptions.SubscriptionAlreadyCancelledException;
import dk.ballebysoftware.billcore.exceptions.UserNotFoundException;
import dk.ballebysoftware.billcore.model.ErrorResponse;
import dk.ballebysoftware.billcore.model.FieldErrorResponse;

/* TODO: Refactor to global handler */
@RestControllerAdvice
public class UserAdvice {
  
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;

    ErrorResponse response = new ErrorResponse(
      LocalDateTime.now(), 
      status.value(),
      "USER_NOT_FOUND",
      ex.getMessage()
    );

    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(DuplicateUserException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateUser(DuplicateUserException ex) {
    HttpStatus status = HttpStatus.CONFLICT;

    ErrorResponse response = new ErrorResponse(
      LocalDateTime.now(), 
      status.value(),
      "DUPLICATE_USER",
      ex.getMessage()
    );

    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    List<FieldErrorResponse> errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(err -> new FieldErrorResponse(
        err.getField(),
        err.getDefaultMessage()
      )).toList();

    ErrorResponse response = new ErrorResponse(
      LocalDateTime.now(), 
      status.value(),
      "VALIDATION_ERROR",
      "Invalid request data",
      errors
    );

    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(SubscriptionAlreadyCancelledException.class)
  public ResponseEntity<ErrorResponse> handleSubscriptionAlreadyCancelled(SubscriptionAlreadyCancelledException ex) {
    HttpStatus status = HttpStatus.CONFLICT;

    ErrorResponse response = new ErrorResponse(
      LocalDateTime.now(), 
      status.value(),
      "SUBSCRIPTION_ALREADY_CANCELLED",
      ex.getMessage()
    );

    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    ErrorResponse response = new ErrorResponse(
      LocalDateTime.now(), 
      status.value(),
      "INTERNAL_ERROR",
      "Something went wrong"
    );

    return ResponseEntity.status(status).body(response);
  }

}
