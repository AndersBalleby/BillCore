package dk.ballebysoftware.billcore.subscription;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  Subscription findFirstByCreatedAtOrderByCreatedAtDesc(LocalDateTime createdAt);
}
