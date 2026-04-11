package dk.ballebysoftware.billcore.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dk.ballebysoftware.billcore.exceptions.user.DuplicateUserException;
import dk.ballebysoftware.billcore.exceptions.user.UserNotFoundException;
import dk.ballebysoftware.billcore.user.dto.CreateUserRequest;
import dk.ballebysoftware.billcore.user.dto.UserResponse;

@Service
public class UserService {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

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

  public UserResponse createUser(CreateUserRequest request) {
    if(repository.existsByEmail(request.getEmail())) {
      throw new DuplicateUserException(request.getEmail());
    }

    User user = new User(
      request.getEmail(),
      passwordEncoder.encode(request.getPassword())
    );

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
