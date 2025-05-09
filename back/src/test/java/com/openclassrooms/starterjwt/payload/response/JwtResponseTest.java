package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class JwtResponseTest {
    @Test
    void testAllGettersAndSetters() {
        JwtResponse jwt = new JwtResponse("tok123", 1L, "user@test.com", "John", "Doe", true);
        assertThat(jwt.getToken()).isEqualTo("tok123");
        assertThat(jwt.getId()).isEqualTo(1L);
        assertThat(jwt.getUsername()).isEqualTo("user@test.com");
        assertThat(jwt.getFirstName()).isEqualTo("John");
        assertThat(jwt.getLastName()).isEqualTo("Doe");
        assertThat(jwt.getAdmin()).isTrue();
        assertThat(jwt.getType()).isEqualTo("Bearer");

        // setters
        jwt.setToken("tok456");
        jwt.setId(2L);
        jwt.setUsername("other@test.com");
        jwt.setFirstName("Jane");
        jwt.setLastName("Smith");
        jwt.setAdmin(false);
        jwt.setType("Custom");
        assertThat(jwt.getToken()).isEqualTo("tok456");
        assertThat(jwt.getId()).isEqualTo(2L);
        assertThat(jwt.getUsername()).isEqualTo("other@test.com");
        assertThat(jwt.getFirstName()).isEqualTo("Jane");
        assertThat(jwt.getLastName()).isEqualTo("Smith");
        assertThat(jwt.getAdmin()).isFalse();
        assertThat(jwt.getType()).isEqualTo("Custom");
    }
} 