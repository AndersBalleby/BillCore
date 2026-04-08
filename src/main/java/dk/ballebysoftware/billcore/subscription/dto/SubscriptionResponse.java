package dk.ballebysoftware.billcore.subscription.dto;

import java.time.LocalDateTime;

public class SubscriptionResponse {
  private Long id;
  private Long userId;
  private String status;
  private LocalDateTime createdAt;

  public SubscriptionResponse(Long id, Long userId, String status, LocalDateTime createdAt) {
    this.id = id;
    this.userId = userId;
    this.status = status;
    this.createdAt = createdAt;
  }

  public Long getId() { return this.id; }
  public Long getUserId() { return this.userId; }
  public String getStatus() { return this.status; }
  public LocalDateTime getCreatedAt() { return this.createdAt; }
}
