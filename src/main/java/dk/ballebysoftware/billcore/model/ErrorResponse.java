package dk.ballebysoftware.billcore.model;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private List<FieldErrorResponse> errors;

  public ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
}

  public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, List<FieldErrorResponse> errors) {
    this(timestamp, status, error, message);
    this.errors = errors;
  }

  public LocalDateTime getTimestamp() { return this.timestamp; }
  public int getStatus() { return this.status; }
  public String getError() { return this.error; }
  public String getMessage() { return this.message; }
  public List<FieldErrorResponse> getErrors() { return this.errors; }
  
}
