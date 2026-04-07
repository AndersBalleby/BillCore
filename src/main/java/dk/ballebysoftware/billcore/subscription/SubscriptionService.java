package dk.ballebysoftware.billcore.subscription;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.ballebysoftware.billcore.exceptions.subscriptions.SubscriptionNotFoundException;
import dk.ballebysoftware.billcore.exceptions.user.UserNotFoundException;
import dk.ballebysoftware.billcore.user.User;
import dk.ballebysoftware.billcore.user.UserRepository;

@Service
public class SubscriptionService {
  
  private final SubscriptionRepository repository;
  private final UserRepository userRepository;

  public SubscriptionService(SubscriptionRepository repository, UserRepository userRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
  }
  
  public Iterable<Subscription> getAllSubscriptions() {
    return repository.findAll();
  }

  public Subscription getSubscriptionById(Long id) {
    return repository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));
  }

  public Subscription createSubscription(CreateSubscriptionRequest request) {
    User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException(request.getUserId()));

    return repository.save(new Subscription(user));
  }

  public void deleteSubscription(Long id) {
    Subscription sub = repository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));

    repository.delete(sub);
  }

  @Transactional
  public void cancelSubscription(Long id) {
    Subscription sub = repository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));
    
    sub.cancel();
  }

}
