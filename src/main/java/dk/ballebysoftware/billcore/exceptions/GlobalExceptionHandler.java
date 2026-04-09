package dk.ballebysoftware.billcore.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dk.ballebysoftware.billcore.api.error.ErrorResponse;
import dk.ballebysoftware.billcore.api.error.FieldErrorResponse;
import dk.ballebysoftware.billcore.exceptions.subscriptions.SubscriptionAlreadyCancelledException;
import dk.ballebysoftware.billcore.exceptions.subscriptions.SubscriptionNotFoundException;
import dk.ballebysoftware.billcore.exceptions.user.DuplicateUserException;
import dk.ballebysoftware.billcore.exceptions.user.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
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

  @ExceptionHandler(SubscriptionNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;

    ErrorResponse response = new ErrorResponse(
      LocalDateTime.now(), 
      status.value(),
      "SUBSCRIPTION_NOT_FOUND",
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

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    ErrorResponse response = new ErrorResponse(
      LocalDateTime.now(), 
      status.value(),
      "MISSING_PARAMETERS",
      ex.getLocalizedMessage()
    );

    return ResponseEntity.status(status).body(response);
  }


}
