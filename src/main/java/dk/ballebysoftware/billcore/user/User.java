package dk.ballebysoftware.billcore.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @NotNull
  private String password;

  protected User() {}

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Long getId() { return this.id; }
  public String getEmail() { return this.email; }
  public String getPassword() { return this.password; }

  public void setEmail(String email) { this.email = email; }
  public void setPassword(String password) { this.password = password; }

  @Override
  public String toString() {
    return "User{id=" + id + ", email='" + email + "'}";
  }
}
