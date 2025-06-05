package com.tuancode.ems.service;

import com.tuancode.ems.controller.auth.AuthenticationRequest;
import com.tuancode.ems.controller.auth.AuthenticationResponse;
import com.tuancode.ems.controller.auth.RegisterRequest;
import com.tuancode.ems.entities.Role;
import com.tuancode.ems.entities.User;
import com.tuancode.ems.repositories.UserRepository;
import com.tuancode.ems.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  // === call Beans ===
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest registerRequest) {
    var user = User.builder()
        .name(registerRequest.getName())
        .email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .role(Role.USER)
        .build();
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest authenRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenRequest.getEmail(),
            authenRequest.getPassword()
        )
    );
    var user = userRepository.findByEmail(authenRequest.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}
