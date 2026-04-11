package dk.ballebysoftware.billcore.invoices;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dk.ballebysoftware.billcore.subscription.Subscription;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  Invoice findFirstBySubscriptionOrderByCreatedAtDesc(Subscription subscription, LocalDateTime createdAt);

  List<Invoice> findByPeriodEndBefore(LocalDateTime beforeDate);

  boolean existsBySubscriptionAndPeriodStart(Subscription subscription, LocalDateTime periodStart);

  Page<Invoice> findBySubscription_UserId(Long userId, Pageable pageable);

  @Query("""
    SELECT i FROM Invoice i
    WHERE (:userId IS NULL OR i.subscription.user.id = :userId)
    AND (:minAmount IS NULL OR i.amount >= :minAmount)
    AND (:maxAmount IS NULL OR i.amount <= :maxAmount)
    AND (CAST(:from AS timestamp) IS NULL OR i.periodStart >= :from)
    AND (CAST(:to AS timestamp) IS NULL OR i.periodEnd <= :to)
  """)
  Page<Invoice> findInvoicesWithFilters(
    @Param("userId") Long userId,
    @Param("minAmount") BigDecimal minAmount,
    @Param("maxAmount") BigDecimal maxAmount,
    @Param("from") LocalDateTime from,
    @Param("to") LocalDateTime to,
    Pageable pageable);
}
