package dk.ballebysoftware.billcore.exceptions;

public class DuplicateUserException extends RuntimeException {
  public DuplicateUserException(String email) {
    super("A user with the email '" + email + "' already exists");
  }

    public DuplicateUserException(Long id) {
    super("A user with the id '" + id + "' already exists");
  }
}
