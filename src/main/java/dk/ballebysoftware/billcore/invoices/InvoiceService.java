package dk.ballebysoftware.billcore.invoices;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dk.ballebysoftware.billcore.exceptions.user.UserNotFoundException;
import dk.ballebysoftware.billcore.invoices.dto.InvoiceResponse;
import dk.ballebysoftware.billcore.user.UserRepository;

@Service
public class InvoiceService {
  
  private final InvoiceRepository repository;
  private final UserRepository userRepository;

  public InvoiceService(InvoiceRepository repository, UserRepository userRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
  }

  public List<InvoiceResponse> getAll() {
    return repository.findAll()
      .stream()
      .map(this::mapToResponse)
      .toList();
  }

  public Page<InvoiceResponse> getInvoicesWithFilters(Long userId, BigDecimal minAmount, BigDecimal maxAmount, LocalDateTime from, LocalDateTime to, Pageable pageable) {

    List<String> allowedSorts = List.of("amount", "createdAt");
    for(Sort.Order order : pageable.getSort()) {
      if(!allowedSorts.contains(order.getProperty())) {
        throw new IllegalArgumentException("Invalid sort field");
      }
    }

    if(userId != null) {
      userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    }

    return repository.findInvoicesWithFilters(
      userId, 
      minAmount, 
      maxAmount, 
      from, 
      to, 
      pageable).map(this::mapToResponse);

  }

  private InvoiceResponse mapToResponse(Invoice invoice) {
    return new InvoiceResponse(
      invoice.getId(),
      invoice.getSubscription().getId(),
      invoice.getAmount(),
      invoice.getPeriodStart(),
      invoice.getPeriodEnd(), 
      invoice.getCreatedAt()
    );
  }

}
