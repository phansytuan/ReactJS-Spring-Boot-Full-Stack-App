package com.tuancode.ems.service;

import com.tuancode.ems.dto.auth.AuthenticationRequestDto;
import com.tuancode.ems.dto.auth.AuthenticationResponseDto;
import com.tuancode.ems.dto.auth.RegisterRequestDto;
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

  public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {
    var user = User.builder()
        .name(registerRequestDto.getName())
        .email(registerRequestDto.getEmail())
        .password(passwordEncoder.encode(registerRequestDto.getPassword()))
        .role(Role.USER)
        .build();
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponseDto.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenRequest.getEmail(),
            authenRequest.getPassword()
        )
    );
    var user = userRepository.findByEmail(authenRequest.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponseDto.builder()
        .token(jwtToken)
        .build();
  }
}