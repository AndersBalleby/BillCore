package dk.ballebysoftware.billcore.api.error;

public class FieldErrorResponse {
  private String field;
  private String message;

  public FieldErrorResponse(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() { return this.field; }
  public String getMessage() { return this.message; }
}
