package dk.ballebysoftware.billcore.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(Long id) {
    super("User not found: " + id);
  }
}