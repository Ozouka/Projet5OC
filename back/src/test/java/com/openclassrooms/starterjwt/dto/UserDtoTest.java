package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoTest {
    @Test
    void testGettersAndSetters() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setEmail("test@test.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setAdmin(true);
        dto.setPassword("password");
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getEmail()).isEqualTo("test@test.com");
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
        assertThat(dto.isAdmin()).isTrue();
        assertThat(dto.getPassword()).isEqualTo("password");
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        UserDto dto1 = new UserDto(1L, "test@test.com", "John", "Doe", true, "password", now, now);
        UserDto dto2 = new UserDto(1L, "test@test.com", "John", "Doe", true, "password", now, now);
        UserDto dto3 = new UserDto(2L, "other@test.com", "Jane", "Smith", false, "pass", now, now);
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
        assertThat(dto1).isNotEqualTo(dto3);
    }

    @Test
    void testToString() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setEmail("test@test.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setAdmin(true);
        dto.setPassword("password");
        String str = dto.toString();
        assertThat(str).contains("test@test.com");
        assertThat(str).contains("John");
        assertThat(str).contains("Doe");
    }

    @Test
    void testCanEqual() {
        UserDto dto = new UserDto();
        assertThat(dto.canEqual(new UserDto())).isTrue();
        assertThat(dto.canEqual("not a UserDto")).isFalse();
    }
} 