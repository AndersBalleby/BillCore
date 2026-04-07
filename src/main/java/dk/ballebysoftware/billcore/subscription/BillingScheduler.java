package dk.ballebysoftware.billcore.subscription;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BillingScheduler {

  private final SubscriptionService subscriptionService;

  public BillingScheduler(SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
  }
  
  @Scheduled(cron = "0 0 1 1 * ?")
  public void generateNewInvoices() {
    subscriptionService.generateMonthlyInvoices();
  }

}
