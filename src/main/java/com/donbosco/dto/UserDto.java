package com.donbosco.dto;

import com.donbosco.models.ERole;

public class UserDto {
    private String username;
    private String password;
    private String email;
    private ERole role;

    
    public UserDto(String username, String password, String email, ERole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    public UserDto() {
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ERole getRole() {
        return role;
    }
    public void setRole(ERole role) {
        this.role = role;
    }

    // Getters y setters
}

