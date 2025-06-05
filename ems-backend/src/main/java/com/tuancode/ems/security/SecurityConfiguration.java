package com.tuancode.ems.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration // Marks this class as a source of bean definitions for Spring
@EnableWebSecurity // Enables Spring Security for the application
@RequiredArgsConstructor // Automatically injects required final fields (constructor injection)
public class SecurityConfiguration {

  // 🔒 Custom JWT filter that checks token validity and sets security context
  private final JwtAuthenticationFilter jwtAuthFilter;

  // 🔐 Defines how authentication should be performed (e.g., using a DAO-based or custom provider)
  private final AuthenticationProvider authenticationProvider;

  /**
   * Defines the main security configuration.
   * @param http HttpSecurity object used to configure web-based security
   * @return The configured SecurityFilterChain
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // 🔒 Disable CSRF (not needed for stateless REST APIs using tokens)
        .csrf(AbstractHttpConfigurer::disable)

        // 🔓 Define public and protected endpoints
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/auth/**").permitAll() // Allow public access to authentication endpoints (e.g., login, register)
            .anyRequest().authenticated() // All other requests must be authenticated
        )

        // ⚙️ Set session management to stateless (no HTTP session — JWT handles authentication)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        // 🔐 Register the custom authentication provider (e.g., DAO, user detail service, etc.)
        .authenticationProvider(authenticationProvider)

        // 🛡️ Add JWT authentication filter before the default username/password filter
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    // 🔚 Build and return the configured security filter chain
    return http.build();
  }
}