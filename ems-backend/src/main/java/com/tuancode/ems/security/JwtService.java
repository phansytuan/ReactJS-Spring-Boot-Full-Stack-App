package com.tuancode.ems.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/* 🔹 3. Validate JWT Token */
@Service // Marks this class as a Spring-managed service bean
public class JwtService {

  // 🔐 Secret key used to sign and verify JWTs (should be securely stored)
  private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

  /**
   * Extracts the username (subject) from a given JWT.
   * @param token JWT string
   * @return Username embedded in the token
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Generic method to extract any claim from the token using a resolver function.
   * @param token JWT string
   * @param claimsResolver Function to extract a specific claim
   * @return The resolved claim value
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Generates a new JWT using only the user details.
   * @param userDetails Spring Security's UserDetails
   * @return Signed JWT string
   */
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Generates a JWT with additional claims.
   * @param extractClaims Custom claims to include in token
   * @param userDetails User details used for subject and authorities
   * @return Signed JWT string
   */
  public String generateToken(
      Map<String, Object> extractClaims,
      UserDetails userDetails)
  {
    return Jwts
        .builder()
        .setClaims(extractClaims) // Add custom claims if needed
        .setSubject(userDetails.getUsername()) // Set subject as username
        .setIssuedAt(new Date(System.currentTimeMillis())) // Issue time
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Token valid for 24 hours
        .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign using HMAC SHA-256
        .compact(); // Generate compact JWT string
  }

  /**
   * Checks if the token is valid for the given user.
   * @param token JWT string
   * @param userDetails User to compare against token subject
   * @return true if valid and not expired
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * Checks if the token is expired.
   * @param token JWT string
   * @return true if token is expired
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from the token.
   * @param token JWT string
   * @return Date of expiration
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Parses and retrieves all claims from the JWT.
   * @param token JWT string
   * @return Claims object containing all token data
   */
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey()) // Use signing key to validate JWT signature
        .build()
        .parseClaimsJws(token)
        .getBody(); // Get the payload (claims)
  }

  /**
   * Decodes the base64 secret key and generates a signing key.
   * @return HMAC-SHA-256 key for signing/validating JWTs
   */
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decode base64 secret
    return Keys.hmacShaKeyFor(keyBytes); // Generate HMAC-SHA key
  }
}

/*
📌 Notes:
- The getSignInKey() method returns a cryptographic key used to sign and verify the JWT.
- The JWT is composed of three parts: Header, Payload (Claims), and Signature.
- The signature ensures:
    🔐 The token was created by a trusted source (server).
    🔒 The token has not been tampered with.
*/