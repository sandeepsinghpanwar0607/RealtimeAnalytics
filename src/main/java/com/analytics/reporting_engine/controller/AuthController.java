package com.analytics.reporting_engine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.analytics.reporting_engine.dto.LoginRequest;
import com.analytics.reporting_engine.dto.LoginResponse;
import com.analytics.reporting_engine.dto.RegisterRequest;
import com.analytics.reporting_engine.entity.User;
import com.analytics.reporting_engine.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}