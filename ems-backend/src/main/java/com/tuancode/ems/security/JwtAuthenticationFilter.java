package com.tuancode.ems.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component // Marks this class as a Spring component (bean) for component scanning
@RequiredArgsConstructor // Generates a constructor for final fields (jwtService, userDetailsService)
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Ensures this filter runs once per request

  private final JwtService jwtService; // Responsible for JWT operations (validation, extraction, etc.)
  private final UserDetailsService userDetailsService; // Loads user data from DB or memory

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Get the "Authorization" header from the HTTP request
    final String authHeader = request.getHeader("Authorization");
    final String jwtToken;
    final String username;

    // If header is missing or doesn't start with "Bearer ", skip JWT validation and pass the request
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response); // Continue with the next filter in the chain
      return;
    }

    // Extract JWT token by removing "Bearer " prefix
    jwtToken = authHeader.substring(7);

    // Extract username (subject) from the token
    username = jwtService.extractUsername(jwtToken);

    // If username is found and user is not already authenticated
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      // Load user details from the UserDetailsService (typically from DB)
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

      // Validate the token against the user details (e.g., expiration, signature)
      if (jwtService.isTokenValid(jwtToken, userDetails)) {

        // Create an authentication token (Spring Security's representation of an authenticated user)
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null, // credentials are not needed here
            userDetails.getAuthorities() // user roles/permissions
        );

        // Attach additional request details (IP, session info, etc.)
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // Set the authentication token into the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // Continue the filter chain (pass the request to the next filter or controller)
    filterChain.doFilter(request, response);
  }
}