package com.tuancode.ems.config;

import com.tuancode.ems.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Marks this class as a Spring configuration class
@RequiredArgsConstructor // Automatically generates a constructor for required final fields
public class ApplicationConfig {

  // Inject the UserRepository to load user details from the database
  private final UserRepository userRepository;

  /**
   * 🔹 Bean for UserDetailsService — used by Spring Security to load user-specific data
   *   @return UserDetailsService lambda that finds a user by email (username)
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByEmail(username) // Searches by email (used as username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found")); // Throws if user doesn't exist
  }

  /**
   * 🔐 Bean for AuthenticationProvider — bridges Spring Security with our userDetailsService & passwordEncoder
   *   @return Configured DaoAuthenticationProvider
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService()); // Set custom user detail service
    authProvider.setPasswordEncoder(passwordEncoder()); // Set password encoder (BCrypt)
    return authProvider;
  }

  /**
   * 🔐 Bean for AuthenticationManager — handles authentication logic (used in AuthService)
   *   @param authConfig Automatically injected AuthenticationConfiguration
   *   @return AuthenticationManager
   *   @throws Exception if authentication manager can't be built
   */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager(); // Delegates creation to Spring
  }

  /**
   * 🔑 Bean for PasswordEncoder — encrypts passwords (used to hash and verify)
   *   @return BCrypt password encoder instance
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // BCrypt is widely used, strong hashing algorithm
  }
}