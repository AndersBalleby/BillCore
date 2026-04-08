package dk.ballebysoftware.billcore.invoices;

import java.util.List;

import org.springframework.stereotype.Service;

import dk.ballebysoftware.billcore.exceptions.user.UserNotFoundException;
import dk.ballebysoftware.billcore.invoices.dto.InvoiceResponse;
import dk.ballebysoftware.billcore.subscription.Subscription;
import dk.ballebysoftware.billcore.subscription.SubscriptionRepository;
import dk.ballebysoftware.billcore.user.UserRepository;

@Service
public class InvoiceService {
  
  private final InvoiceRepository repository;
  private final SubscriptionRepository subscriptionRepository;
  private final UserRepository userRepository;

  public InvoiceService(InvoiceRepository repository, SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
    this.repository = repository;
    this.subscriptionRepository = subscriptionRepository;
    this.userRepository = userRepository;
  }

  /* TODO: Add pagination */
  public List<Invoice> getAll() {
    return repository.findAll();
  }

  public List<InvoiceResponse> getInvoicesByUserId(Long id) {
    userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    return repository.findBySubscription_UserId(id)
      .stream()
      .map(this::mapToResponse)
      .toList();
  }

  private InvoiceResponse mapToResponse(Invoice invoice) {
    return new InvoiceResponse(
      invoice.getId(),
      invoice.getSubscription().getId(),
      invoice.getAmount(),
      invoice.getPeriodStart(),
      invoice.getPeriodEnd(), 
      invoice.getCreatedAt()
    );
  }

}
