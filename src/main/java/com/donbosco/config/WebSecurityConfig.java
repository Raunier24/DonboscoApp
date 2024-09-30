package com.donbosco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.donbosco.jwt.AuthTokenFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

        private final AuthenticationProvider authenticationProvider;
        private final AuthTokenFilter authTokenFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authRequest -> authRequest
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/flights/getAll").hasAnyAuthority("ADMIN", "USER")
                                                .requestMatchers("/api/flights/id").hasAuthority("ADMIN")
                                                .requestMatchers("/api/flights/create").hasAuthority("USER")
                                                .requestMatchers("/api/flights/update").hasAuthority("USER")
                                                .requestMatchers("/api/flights/delete").hasAuthority("USER")

                                                .requestMatchers("/api/reservation/getAll").hasAnyAuthority("ADMIN", "USER")
                                                .requestMatchers("/api/reservation/id").hasAuthority("ADMIN")
                                                .requestMatchers("/api/reservation/create").hasAuthority("USER")
                                                .requestMatchers("/api/reservation/update").hasAuthority("USER")
                                                .requestMatchers("/api/reservation/delete").hasAuthority("USER")

                                                .requestMatchers("/api/user/getAll").hasAuthority("ADMIN")
                                                .requestMatchers("/api/user/id").hasAuthority("ADMIN")
                                                .requestMatchers("/api/user/create").hasAuthority("USER")
                                                .requestMatchers("/api/user/update").hasAuthority("USER")
                                                .requestMatchers("/api/user/delete").hasAnyAuthority("ADMIN", "USER")

                                                .anyRequest().authenticated())
                                .sessionManagement(sessionManager -> sessionManager
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}