package com.donbosco.dto.request;

import com.scrumapp.model.ERole;

public class RegisterResponse {
    private ERole role;


    public RegisterResponse(ERole role, String message) {
        this.role = role;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

}
