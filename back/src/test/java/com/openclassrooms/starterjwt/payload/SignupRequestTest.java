package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SignupRequestTest {
    @Test
    void testGettersAndSetters() {
        SignupRequest req = new SignupRequest();
        req.setEmail("test@test.com");
        req.setPassword("password");
        req.setFirstName("John");
        req.setLastName("Doe");
        assertThat(req.getEmail()).isEqualTo("test@test.com");
        assertThat(req.getPassword()).isEqualTo("password");
        assertThat(req.getFirstName()).isEqualTo("John");
        assertThat(req.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testEqualsAndHashCode() {
        SignupRequest req1 = new SignupRequest();
        req1.setEmail("test@test.com");
        req1.setPassword("password");
        req1.setFirstName("John");
        req1.setLastName("Doe");
        SignupRequest req2 = new SignupRequest();
        req2.setEmail("test@test.com");
        req2.setPassword("password");
        req2.setFirstName("John");
        req2.setLastName("Doe");
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }

    @Test
    void testToString() {
        SignupRequest req = new SignupRequest();
        assertThat(req.toString()).contains("SignupRequest");
    }
} 