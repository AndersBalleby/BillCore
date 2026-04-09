package dk.ballebysoftware.billcore.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dk.ballebysoftware.billcore.user.dto.UserResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {
  
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  /* TODO: Add pagination */
  @GetMapping
  public Page<UserResponse> all(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    return service.getAllUsers(pageable);
  }

  @GetMapping("/{id}")
  public UserResponse getUser(@PathVariable Long id) {
    return service.getUserById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse createUser(@RequestBody @Valid User user) {
    return service.createUser(user);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.deleteUser(id);
  }
}
