package dk.ballebysoftware.billcore.invoices;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
  
  private final InvoiceRepository repository;

  public InvoiceService(InvoiceRepository repository) {
    this.repository = repository;
  }

  /* TODO: Add pagination */
  public List<Invoice> getAll() {
    return repository.findAll();
  }

  

}
