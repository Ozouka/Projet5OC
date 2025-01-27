package com.openclassrooms.starterjwt.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

@SpringBootTest
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void generateJwtToken_ShouldReturnValidToken() {
        // GIVEN
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
            .id(1L)
            .username("test@test.com")
            .firstName("Test")
            .lastName("User")
            .password("test1234")
            .build();
            
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );

        // WHEN
        String token = jwtUtils.generateJwtToken(authentication);

        // THEN
        assertThat(token).isNotNull();
        assertThat(jwtUtils.validateJwtToken(token)).isTrue();
        assertThat(jwtUtils.getUserNameFromJwtToken(token)).isEqualTo(userDetails.getUsername());
    }

    @Test
    void validateJwtToken_ShouldReturnFalse_WhenTokenIsInvalid() {
        // GIVEN
        String invalidToken = "invalid.token.here";

        // WHEN & THEN
        assertThat(jwtUtils.validateJwtToken(invalidToken)).isFalse();
    }
} 