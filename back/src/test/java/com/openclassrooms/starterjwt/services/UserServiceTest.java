package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        // GIVEN
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setId(userId);
        expectedUser.setEmail("test@test.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // WHEN
        User result = userService.findById(userId);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        verify(userRepository).findById(userId);
    }

    @Test
    void findById_ShouldReturnNull_WhenUserDoesNotExist() {
        // GIVEN
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // WHEN
        User result = userService.findById(userId);

        // THEN
        assertThat(result).isNull();
        verify(userRepository).findById(userId);
    }
} 