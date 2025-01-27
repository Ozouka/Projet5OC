package com.openclassrooms.starterjwt.security.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setPassword("test1234");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        // WHEN
        UserDetails result = userDetailsService.loadUserByUsername("test@test.com");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("test@test.com");
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        // GIVEN
        when(userRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("unknown@test.com"))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("User Not Found with email: unknown@test.com");
    }
} 