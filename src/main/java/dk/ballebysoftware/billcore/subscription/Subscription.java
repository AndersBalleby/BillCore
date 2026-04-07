package dk.ballebysoftware.billcore.subscription;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import dk.ballebysoftware.billcore.exceptions.subscriptions.SubscriptionAlreadyCancelledException;
import dk.ballebysoftware.billcore.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "subscriptions")
public class Subscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @NotNull
  @Enumerated(EnumType.STRING)
  private SubscriptionStatus status;

  @CreationTimestamp
  private LocalDateTime createdAt;

  protected Subscription() {}

  public Subscription(User user) {
    this.user = user;
    this.status = SubscriptionStatus.ACTIVE;
  }

  /* TODO: Check if subscription is already cancelled? */
  public void cancel() {
    if(this.status == SubscriptionStatus.CANCELLED) {
      throw new SubscriptionAlreadyCancelledException(id);
    }

    this.status = SubscriptionStatus.CANCELLED;
  }

  public Long getId() { return this.id; }
  public User getUser() { return this.user; }
  public SubscriptionStatus getStatus() { return this.status; }
  public LocalDateTime getCreatedAt() { return this.createdAt; }


}


