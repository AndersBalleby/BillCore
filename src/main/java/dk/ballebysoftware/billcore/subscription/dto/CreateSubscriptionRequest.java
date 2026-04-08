package dk.ballebysoftware.billcore.subscription.dto;

import jakarta.validation.constraints.NotNull;

public class CreateSubscriptionRequest {
  
  @NotNull
  private Long userId;

  public Long getUserId() { return userId; }
}
