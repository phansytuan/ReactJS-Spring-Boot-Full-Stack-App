package com.tuancode.ems.controller.auth;

import com.tuancode.ems.dto.auth.AuthenticationRequestDto;
import com.tuancode.ems.dto.auth.AuthenticationResponseDto;
import com.tuancode.ems.dto.auth.RegisterRequestDto;
import com.tuancode.ems.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponseDto> register(
      @RequestBody RegisterRequestDto registerRequestDto) {
    return ResponseEntity.ok(authenService.register(registerRequestDto));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponseDto> register(
      @RequestBody AuthenticationRequestDto authenRequest) {
    return ResponseEntity.ok(authenService.authenticate(authenRequest));
  }
}