package dk.ballebysoftware.billcore.user.dto;

public class UserResponse {
  private Long id;
  private String email;

  public UserResponse(Long id, String email) {
    this.id = id;
    this.email = email;
  }

  public Long getId() { return this.id; }
  public String getEmail() { return this.email; }
}
