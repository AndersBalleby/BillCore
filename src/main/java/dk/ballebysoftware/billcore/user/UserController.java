package dk.ballebysoftware.billcore.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {
  
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public Iterable<User> all() {
    return service.getAllUsers();
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable Long id) {
    return service.getUserById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User createUser(@RequestBody @Valid User user) {
    return service.createUser(user);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.deleteUser(id);
  }
}
