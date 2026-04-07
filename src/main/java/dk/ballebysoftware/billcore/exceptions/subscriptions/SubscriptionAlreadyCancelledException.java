package dk.ballebysoftware.billcore.exceptions.subscriptions;

public class SubscriptionAlreadyCancelledException extends RuntimeException {
  public SubscriptionAlreadyCancelledException(Long id) {
    super("The subscription with id='" + id + "' is already cancelled");
  }
}
