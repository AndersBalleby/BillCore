package dk.ballebysoftware.billcore.subscription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dk.ballebysoftware.billcore.subscription.dto.CreateSubscriptionRequest;
import dk.ballebysoftware.billcore.subscription.dto.SubscriptionResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
  
  private final SubscriptionService service;

  public SubscriptionController(SubscriptionService service) {
    this.service = service;
  }

  @GetMapping
  public Page<SubscriptionResponse> getAllSubscriptions(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return service.getAllSubscriptions(pageable);
  }

  @GetMapping("/{id}")
  public SubscriptionResponse getSubscription(@PathVariable Long id) {
    return service.getSubscriptionById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SubscriptionResponse createSubscription(@RequestBody @Valid CreateSubscriptionRequest request) {
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
