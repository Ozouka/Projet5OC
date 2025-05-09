package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import java.util.Arrays;
import java.time.LocalDateTime;

public class SessionDtoTest {
    @Test
    void testGettersAndSetters() {
        SessionDto dto = new SessionDto();
        dto.setId(1L);
        dto.setName("Yoga");
        dto.setDescription("Cours de yoga");
        dto.setTeacher_id(2L);
        Date now = new Date();
        dto.setDate(now);
        dto.setUsers(Arrays.asList(1L, 2L));
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        dto.setCreatedAt(created);
        dto.setUpdatedAt(updated);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Yoga");
        assertThat(dto.getDescription()).isEqualTo("Cours de yoga");
        assertThat(dto.getTeacher_id()).isEqualTo(2L);
        assertThat(dto.getDate()).isEqualTo(now);
        assertThat(dto.getUsers()).containsExactly(1L, 2L);
        assertThat(dto.getCreatedAt()).isEqualTo(created);
        assertThat(dto.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void testFullConstructor() {
        Date now = new Date();
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        SessionDto dto = new SessionDto(1L, "Yoga", now, 2L, "Cours de yoga", Arrays.asList(1L, 2L), created, updated);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Yoga");
        assertThat(dto.getDescription()).isEqualTo("Cours de yoga");
        assertThat(dto.getTeacher_id()).isEqualTo(2L);
        assertThat(dto.getDate()).isEqualTo(now);
        assertThat(dto.getUsers()).containsExactly(1L, 2L);
        assertThat(dto.getCreatedAt()).isEqualTo(created);
        assertThat(dto.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void testEqualsAndHashCode() {
        Date now = new Date();
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        SessionDto dto1 = new SessionDto(1L, "Yoga", now, 2L, "Cours de yoga", Arrays.asList(1L, 2L), created, updated);
        SessionDto dto2 = new SessionDto(1L, "Yoga", now, 2L, "Cours de yoga", Arrays.asList(1L, 2L), created, updated);
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void testToString() {
        SessionDto dto = new SessionDto();
        assertThat(dto.toString()).contains("SessionDto");
    }

    @Test
    void testCanEqual() {
        SessionDto dto = new SessionDto();
        assertThat(dto.canEqual(new SessionDto())).isTrue();
    }
} 