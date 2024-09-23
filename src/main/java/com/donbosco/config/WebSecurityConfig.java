package com.donbosco.config;

import com.donbosco.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthTokenFilter authTokenFilter;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider, AuthTokenFilter authTokenFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authTokenFilter = authTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/api/auth/login").permitAll()//POST
                                .requestMatchers("/api/auth/register").hasAnyAuthority("ADMIN")//POST
                                .requestMatchers("/api/user/").hasAnyAuthority("ADMIN")//GETALL
                                .requestMatchers("/api/user/{id}").hasAnyAuthority("ADMIN", "USER")//GETID
                                .requestMatchers("/api/user/create").hasAnyAuthority("ADMIN")//CREATE
                                .requestMatchers("/api/user/update/{id}").hasAnyAuthority("ADMIN")//PUT
                                .requestMatchers("/api/user/delete/{id}").hasAnyAuthority("ADMIN")//DELETEID
                                .requestMatchers("/api/project/").permitAll()//GETALL
                                .requestMatchers("/api/project/{id}").permitAll()//GETID
                                .requestMatchers("/api/project/create").hasAnyAuthority("ADMIN", "MANAGER")//POST
                                .requestMatchers("/api/project/update/{id}").hasAnyAuthority("ADMIN", "MANAGER")//PUTID
                                .requestMatchers("/api/project/delete/{id}").hasAnyAuthority("ADMIN", "MANAGER")//DELETEID
                                .requestMatchers("/api/task/**").hasAnyAuthority("USER","MANAGER")//GETALL
                                .requestMatchers("/api/task/{id}").hasAnyAuthority("USER")//GETID
                                .requestMatchers("/api/task/create").hasAnyAuthority("MANAGER")//POST
                                .requestMatchers("/api/task/update/{id}").hasAnyAuthority("USER")//PUTID
                                .requestMatchers("/api/task/delete/{id}").hasAnyAuthority("MANAGER")//DELETEID
                                .anyRequest().authenticated()

                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
