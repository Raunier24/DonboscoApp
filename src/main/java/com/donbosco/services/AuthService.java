package com.donbosco.services;

import com.donbosco.dto.request.AuthResponse;
import com.donbosco.dto.request.RegisterResponse;
import com.donbosco.dto.response.LoginRequest;
import com.donbosco.dto.response.RegisterRequest;
import com.donbosco.models.User;
import com.donbosco.repositories.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {

    private final JwtService jwtService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(JwtService jwtService, IUserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        UserDetails user = (UserDetails) userRepository.findByUsername(login.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtService.getTokenService(user);

        return new AuthResponse
                .Builder()
                .token(token)
                .role(((User) user).getRole())
                .build();
    }

    public RegisterResponse register(RegisterRequest register) {
        User user = new User.Builder()
                .username(register.getUsername())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(register.getRole())
                .build();

        userRepository.save(user);

        return new RegisterResponse(register.getRole(), "User registered successfully");
    }
}