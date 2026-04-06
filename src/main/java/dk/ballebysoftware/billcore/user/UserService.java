package dk.ballebysoftware.billcore.user;

import org.springframework.stereotype.Service;

import dk.ballebysoftware.billcore.exceptions.DuplicateUserException;
import dk.ballebysoftware.billcore.exceptions.UserNotFoundException;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  } 

  public Iterable<User> getAllUsers() {
    return repository.findAll();
  }

  public User getUserById(Long id) {
    return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  public User createUser(User user) {
    if(repository.existsByEmail(user.getEmail())) {
      throw new DuplicateUserException(user.getEmail());
    }

    return repository.save(user);
  }

  public void deleteUser(Long id) {
    if(!repository.existsById(id)) {
      throw new UserNotFoundException(id);
    }

    repository.deleteById(id);
  }

  
}
