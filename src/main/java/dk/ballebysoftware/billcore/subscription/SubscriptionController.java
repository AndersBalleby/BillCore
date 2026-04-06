package dk.ballebysoftware.billcore.subscription;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
  
  private final SubscriptionService service;

  public SubscriptionController(SubscriptionService service) {
    this.service = service;
  }

  @GetMapping
  public Iterable<Subscription> getAllSubscriptions() {
    return service.getAllSubscriptions();
  }

  @GetMapping("/{id}")
  public Subscription getSubscription(@PathVariable Long id) {
    return service.getSubscriptionById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Subscription createSubscription(@RequestBody @Valid CreateSubscriptionRequest request) {
    return service.createSubscription(request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.deleteSubscription(id);
  }

  @PostMapping("/{id}/cancel")
  @ResponseStatus(HttpStatus.OK)
  public void cancel(@PathVariable Long id) {
    service.cancelSubscription(id);
  }
}
