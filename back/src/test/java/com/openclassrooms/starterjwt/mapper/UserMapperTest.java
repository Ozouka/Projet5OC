package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {
    private final UserMapperImpl mapper = new UserMapperImpl();

    @Test
    void testToDto() {
        User user = User.builder()
            .id(1L)
            .email("test@test.com")
            .lastName("Doe")
            .firstName("John")
            .password("password")
            .admin(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        UserDto dto = mapper.toDto(user);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
        assertThat(dto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(dto.getLastName()).isEqualTo(user.getLastName());
        assertThat(dto.isAdmin()).isEqualTo(user.isAdmin());
    }

    @Test
    void testToEntity() {
        UserDto dto = new UserDto(1L, "test@test.com", "Doe", "John", true, "password", LocalDateTime.now(), LocalDateTime.now());
        User user = mapper.toEntity(dto);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(dto.getId());
        assertThat(user.getEmail()).isEqualTo(dto.getEmail());
        assertThat(user.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(user.getLastName()).isEqualTo(dto.getLastName());
        assertThat(user.isAdmin()).isEqualTo(dto.isAdmin());
    }
} 