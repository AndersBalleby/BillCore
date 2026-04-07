package dk.ballebysoftware.billcore.exceptions.subscriptions;

public class SubscriptionNotFoundException extends RuntimeException {
  public SubscriptionNotFoundException(Long id) {
    super("Subscription with id='" + id + "' could not be found");
  }
}
