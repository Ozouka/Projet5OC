package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginRequestTest {
    @Test
    void testGettersAndSetters() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("password");
        assertThat(req.getEmail()).isEqualTo("test@test.com");
        assertThat(req.getPassword()).isEqualTo("password");
    }
} 