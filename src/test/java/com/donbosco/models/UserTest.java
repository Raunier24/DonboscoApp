package com.donbosco.models;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGetAuthorities() {
        User user = new User.Builder()
                .username("testUser")
                .password("password")
                .role(ERole.USER)
                .build();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("USER", authorities.iterator().next().getAuthority());
    }

    @Test
    void testIsAccountNonExpired() {
        User user = new User.Builder().build();
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        User user = new User.Builder().build();
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        User user = new User.Builder().build();
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        User user = new User.Builder().build();
        assertTrue(user.isEnabled());
    }
}