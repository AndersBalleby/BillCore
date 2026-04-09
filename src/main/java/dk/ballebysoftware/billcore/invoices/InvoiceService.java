package dk.ballebysoftware.billcore.invoices;

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

  public Page<InvoiceResponse> getInvoicesByUserId(Long id, Pageable pageable) {

    List<String> allowedSorts = List.of("amount", "createdAt");
    for(Sort.Order order : pageable.getSort()) {
      if(!allowedSorts.contains(order.getProperty())) {
        throw new IllegalArgumentException("Invalid sort field");
      }
    }

    userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    return repository.findBySubscription_UserId(id, pageable).map(this::mapToResponse);
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
