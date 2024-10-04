package com.donbosco.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ERoleTest {
    @Test
    void testERole_ADMIN() {
        // Given
        ERole role = ERole.ADMIN;

        // Then
        assertEquals(ERole.ADMIN, role);
    }

    @Test
    void testERole_USER() {
        // Given
        ERole role = ERole.USER;

        // Then
        assertEquals(ERole.USER, role);
    }

    @Test
    void testERole_values() {
        // Given
        ERole[] roles = ERole.values();

        // Then
        assertEquals(2, roles.length);
        assertEquals(ERole.ADMIN, roles[0]);
        assertEquals(ERole.USER, roles[1]);
    }

    @Test
    void testERole_valueOf() {
        // Given
        ERole role = ERole.valueOf("ADMIN");

        // Then
        assertEquals(ERole.ADMIN, role);
    }

    @Test
    void testERole_valueOf_Invalid() {
        // Given
        try {
            ERole.valueOf("INVALID");
        } catch (IllegalArgumentException e) {
            // Then
            assertEquals("No enum constant com.donbosco.models.ERole.INVALID", e.getMessage());
        }
    }
}