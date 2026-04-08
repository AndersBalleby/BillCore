package dk.ballebysoftware.billcore.invoices.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceResponse {
  
  private Long id;
  private Long subscriptionId;
  private BigDecimal amount;
  private LocalDateTime periodStart;
  private LocalDateTime periodEnd;
  private LocalDateTime createdAt;

  public InvoiceResponse(Long id, Long subscriptionId, BigDecimal amount, LocalDateTime periodStart, LocalDateTime periodEnd, LocalDateTime createdAt) {
    this.id = id;
    this.subscriptionId = subscriptionId;
    this.amount = amount;
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
    this.createdAt = createdAt;
  }

  public Long getId() { return this.id; }
  public Long getSubscriptionId() { return this.subscriptionId; }
  public BigDecimal getAmount() { return this.amount; }
  public LocalDateTime getPeriodStart() { return this.periodStart; }
  public LocalDateTime getPeriodEnd() { return this.periodEnd; }
  public LocalDateTime getCreatedAt() { return this.createdAt; }
}
