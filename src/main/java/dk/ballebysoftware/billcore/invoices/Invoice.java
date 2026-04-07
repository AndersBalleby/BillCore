package dk.ballebysoftware.billcore.invoices;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import dk.ballebysoftware.billcore.subscription.Subscription;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "invoices")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "subscription_id", nullable = false)
  private Subscription subscription;

  @NotNull
  private BigDecimal amount;

  @NotNull
  private LocalDateTime periodStart;

  @NotNull
  private LocalDateTime periodEnd;

  @CreationTimestamp
  LocalDateTime createdAt;

  protected Invoice() {}

  public Invoice(Subscription subscription, BigDecimal amount, LocalDateTime periodStart, LocalDateTime periodEnd) {
    this.subscription = subscription;
    this.amount = amount;
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
  }

  public Long getId() { return this.id; }
  public Subscription getSubscription() { return this.subscription; }
  public BigDecimal getAmount() { return this.amount; }
  public LocalDateTime getCreatedAt() { return this.createdAt; }
  public LocalDateTime getPeriodStart() { return this.periodStart; }
  public LocalDateTime getPeriodEnd() { return this.periodEnd; }
}
