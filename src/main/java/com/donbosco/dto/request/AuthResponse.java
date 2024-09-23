package com.donbosco.dto.request;

import com.scrumapp.model.ERole;


public class AuthResponse {
    String token;
    ERole role;

    private AuthResponse(Builder builder) {
        this.token = builder.token;
        this.role = builder.role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public static class Builder {
        private String token;
        private ERole role;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder role(ERole role) {
            this.role = role;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }
    }
}
