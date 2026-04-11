package dk.ballebysoftware.billcore.api.auth;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
  private static final String SECRET = "mysupersecretkeymysupersecretkey12345665646456456";

  public String generateToken(String email) {
    return Jwts.builder()
      .setSubject(email)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
      .signWith(SignatureAlgorithm.HS256, SECRET) /* TODO: Deprecated */
      .compact();
  }

  /* TODO: Refactor, deprecated */
  public String extractEmail(String token) {
    return Jwts.parser()
      .setSigningKey(SECRET)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }
}
