package dk.ballebysoftware.billcore.invoices;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dk.ballebysoftware.billcore.invoices.dto.InvoiceResponse;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
  
  private final InvoiceService service;

  public InvoiceController(InvoiceService service) {
    this.service = service;
  }

  /* TODO: Check if user exists */
  @GetMapping
  public List<InvoiceResponse> getInvoices(@RequestParam(name = "userId") @NotNull Long userId) {
    
    return service.getInvoicesByUserId(userId);
  }
  
}
