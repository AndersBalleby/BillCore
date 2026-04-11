package dk.ballebysoftware.billcore.invoices;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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
    @RequestParam(required = false) Long userId,
    @RequestParam(required = false) BigDecimal minAmount,
    @RequestParam(required = false) BigDecimal maxAmount,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
    Pageable pageable
  ) 
  {
    return service.getInvoicesWithFilters(
      userId,
      minAmount,
      maxAmount,
      from,
      to,
      pageable
    );
  }
  
}
