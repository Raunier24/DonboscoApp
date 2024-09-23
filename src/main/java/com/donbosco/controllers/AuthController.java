package com.donbosco.controllers;

import com.scrumapp.dto.request.AuthResponse;
import com.scrumapp.dto.request.RegisterResponse;
import com.scrumapp.dto.response.LoginRequest;
import com.scrumapp.dto.response.RegisterRequest;
import com.scrumapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
}
