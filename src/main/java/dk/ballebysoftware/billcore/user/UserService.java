package dk.ballebysoftware.billcore.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dk.ballebysoftware.billcore.exceptions.user.DuplicateUserException;
import dk.ballebysoftware.billcore.exceptions.user.UserNotFoundException;
import dk.ballebysoftware.billcore.user.dto.UserResponse;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  } 

  public Page<UserResponse> getAllUsers(Pageable pageable) {
    return repository.findAll(pageable).map(this::mapToResponse);
  }

  public UserResponse getUserById(Long id) {
    User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    
    return mapToResponse(user);
  }

  public UserResponse createUser(User user) {
    if(repository.existsByEmail(user.getEmail())) {
      throw new DuplicateUserException(user.getEmail());
    }

    return mapToResponse(repository.save(user));
  }

  public void deleteUser(Long id) {
    if(!repository.existsById(id)) {
      throw new UserNotFoundException(id);
    }

    repository.deleteById(id);
  }

  private UserResponse mapToResponse(User user) {
    return new UserResponse(user.getId(), user.getEmail());
  }

  
}
