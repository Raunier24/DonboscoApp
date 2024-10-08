package com.donbosco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                                .csrf(csrf -> 
                                        csrf.disable())
                                .authorizeHttpRequests(authRequest -> 
                                        authRequest
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/test/**").permitAll()
                                                .requestMatchers("/api/sales/reserve").permitAll()
                                                .requestMatchers("/actuator").permitAll()

                                                .requestMatchers("/api/flights/departure/{departure}").permitAll()
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

                                                .requestMatchers("/api/users").hasAuthority("ADMIN")
                                                .requestMatchers("/api/users/id").hasAuthority("ADMIN")
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

        @Bean
        public UrlBasedCorsConfigurationSource corsConfigurationSource() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("http://127.0.0.1:5500"); // CORS configuration
            config.addAllowedHeader("*");
            config.addAllowedMethod("*"); // Permitir todos los métodos
            source.registerCorsConfiguration("/**", config);
            return source;
        }
}