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
                                                .requestMatchers("/api/auth/login").permitAll()
                                                .requestMatchers("/api/auth/register").permitAll()
                                                .requestMatchers("/api/new/vuelo").hasAuthority("ADMIN")
                                                .requestMatchers("/api/post/delete/**").hasAuthority("ADMIN")
                                                .requestMatchers("/api/post/update/**").hasAuthority("ADMIN")
                                                .requestMatchers("/api/post/getAll").permitAll()
                                                .requestMatchers("/api/donations").hasAnyAuthority("ADMIN", "USER")
                                                .requestMatchers("/api/donations/delete/**").hasAuthority("ADMIN")
                                                .requestMatchers("/api/donations/update/**").hasAuthority("ADMIN")
                                                .requestMatchers("/api/donations/getAll").permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(sessionManager -> sessionManager
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}