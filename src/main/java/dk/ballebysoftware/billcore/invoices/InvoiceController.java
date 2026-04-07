package dk.ballebysoftware.billcore.invoices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
  
  private final InvoiceService service;

  public InvoiceController(InvoiceService service) {
    this.service = service;
  }
  
}
