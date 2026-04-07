package dk.ballebysoftware.billcore.subscription;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.ballebysoftware.billcore.exceptions.subscriptions.SubscriptionNotFoundException;
import dk.ballebysoftware.billcore.exceptions.user.UserNotFoundException;
import dk.ballebysoftware.billcore.invoices.Invoice;
import dk.ballebysoftware.billcore.invoices.InvoiceRepository;
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
  
  public Iterable<Subscription> getAllSubscriptions() {
    return repository.findAll();
  }

  public Subscription getSubscriptionById(Long id) {
    return repository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));
  }

  @Transactional
  public Subscription createSubscription(CreateSubscriptionRequest request) {
    User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException(request.getUserId()));

    Subscription subscription = repository.save(new Subscription(user));
    Invoice invoice = createInitialinvoice(subscription);
    invoiceRepository.save(invoice);

    return subscription;
  }

  private Invoice createInitialinvoice(Subscription subscription) {
    final LocalDateTime periodStart = LocalDateTime.now();
    final LocalDateTime periodEnd = periodStart.plusMonths(1);
    Invoice invoice = new Invoice(subscription, DEFAULT_PRICE, periodStart, periodEnd);

    return invoice;
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

  public void generateMonthlyInvoices() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'generateMonthlyInvoices'");
  }

}
