package dk.ballebysoftware.billcore.invoices;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dk.ballebysoftware.billcore.subscription.Subscription;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  Invoice findFirstBySubscriptionOrderByCreatedAtDesc(Subscription subscription, LocalDateTime createdAt);

  List<Invoice> findByPeriodEndBefore(LocalDateTime beforeDate);

  boolean existsBySubscriptionAndPeriodStart(Subscription subscription, LocalDateTime periodStart);

  Page<Invoice> findBySubscription_UserId(Long userId, Pageable pageable);
}
