package dk.ballebysoftware.billcore.user.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {
  
  @NotBlank private String email;
  @NotBlank private String password;

  public CreateUserRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() { return this.email; }
  public String getPassword() { return this.password; }
}
