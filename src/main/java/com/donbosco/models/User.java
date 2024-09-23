package com.donbosco.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements org.springframework.security.core.userdetails.UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    ERole role;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))

    private Set<String> roles = new HashSet<>();

    public User() {}

    public User(String username, String password, String email, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
