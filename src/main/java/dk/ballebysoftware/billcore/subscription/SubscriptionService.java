package dk.ballebysoftware.billcore.subscription;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.ballebysoftware.billcore.exceptions.subscriptions.SubscriptionNotFoundException;
import dk.ballebysoftware.billcore.exceptions.user.UserNotFoundException;
import dk.ballebysoftware.billcore.invoices.Invoice;
import dk.ballebysoftware.billcore.invoices.InvoiceRepository;
import dk.ballebysoftware.billcore.subscription.dto.CreateSubscriptionRequest;
import dk.ballebysoftware.billcore.subscription.dto.SubscriptionResponse;
import dk.ballebysoftware.billcore.user.User;
import dk.ballebysoftware.billcore.user.UserRepository;

@Service
public class SubscriptionService {
  
  private static final BigDecimal DEFAULT_PRICE = new BigDecimal("99.99");

  private final SubscriptionRepository repository;
  private final UserRepository userRepository;
  private final InvoiceRepository invoiceRepository;

  public SubscriptionService(SubscriptionRepository repository, UserRepository userRepository, InvoiceRepository invoiceRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
    this.invoiceRepository = invoiceRepository;
  }
  
  public Iterable<SubscriptionResponse> getAllSubscriptions() {
    return repository.findAll()
    .stream()
    .map(this::mapToResponse)
    .toList();
  }

  public SubscriptionResponse getSubscriptionById(Long id) {
    Subscription subscription = repository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));

    return mapToResponse(subscription);
  }

  @Transactional
  public SubscriptionResponse createSubscription(CreateSubscriptionRequest request) {
    User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException(request.getUserId()));

    Subscription subscription = repository.save(new Subscription(user));
    Invoice invoice = createInitialinvoice(subscription);
    invoiceRepository.save(invoice);

    return mapToResponse(subscription);
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

  /* TODO: Better performance */
  @Transactional
  public void generateMonthlyInvoices() {
    List<Subscription> activeSubscriptions = repository.findByStatus(SubscriptionStatus.ACTIVE);

    final LocalDateTime now = LocalDateTime.now();
    final LocalDateTime periodStart = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
    final LocalDateTime periodEnd = periodStart.plusMonths(1);

    for(Subscription subscription : activeSubscriptions) {
      if(!invoiceRepository.existsBySubscriptionAndPeriodStart(subscription, periodStart)) {
        invoiceRepository.save(new Invoice(subscription, DEFAULT_PRICE, periodStart, periodEnd));
      }
    }
  }

  private Invoice createInitialinvoice(Subscription subscription) {
    final LocalDateTime periodStart = LocalDateTime.now();
    final LocalDateTime periodEnd = periodStart.plusMonths(1);
    Invoice invoice = new Invoice(subscription, DEFAULT_PRICE, periodStart, periodEnd);

    return invoice;
  }

  private SubscriptionResponse mapToResponse(Subscription subscription) {
    return new SubscriptionResponse(
      subscription.getId(),
      subscription.getUser().getId(),
      subscription.getStatus().toString(),
      subscription.getCreatedAt()
    );
  }

}
