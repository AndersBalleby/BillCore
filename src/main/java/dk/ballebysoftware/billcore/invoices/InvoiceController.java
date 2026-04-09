package dk.ballebysoftware.billcore.invoices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dk.ballebysoftware.billcore.invoices.dto.InvoiceResponse;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
  
  private final InvoiceService service;

  public InvoiceController(InvoiceService service) {
    this.service = service;
  }

  @GetMapping
  public Page<InvoiceResponse> getInvoices(
    @RequestParam Long userId,
    @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
    Pageable pageable) 
  {
    return service.getInvoicesByUserId(userId, pageable);
  }
  
}
