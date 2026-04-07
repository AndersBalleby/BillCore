package dk.ballebysoftware.billcore.invoices;

import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
  
  private final InvoiceRepository repository;

  public InvoiceService(InvoiceRepository repository) {
    this.repository = repository;
  }

}
