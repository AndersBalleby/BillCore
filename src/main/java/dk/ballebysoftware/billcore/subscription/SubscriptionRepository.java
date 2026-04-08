package dk.ballebysoftware.billcore.subscription;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  Subscription findFirstByCreatedAtOrderByCreatedAtDesc(LocalDateTime createdAt);

  List<Subscription> findByStatus(SubscriptionStatus status);
}
