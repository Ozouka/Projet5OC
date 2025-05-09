package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class TeacherDtoTest {
    @Test
    void testGettersAndSetters() {
        TeacherDto dto = new TeacherDto();
        dto.setId(1L);
        dto.setFirstName("Margot");
        dto.setLastName("Delahaye");
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getFirstName()).isEqualTo("Margot");
        assertThat(dto.getLastName()).isEqualTo("Delahaye");
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testFullConstructor() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        TeacherDto dto = new TeacherDto(1L, "Delahaye", "Margot", created, updated);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getLastName()).isEqualTo("Delahaye");
        assertThat(dto.getFirstName()).isEqualTo("Margot");
        assertThat(dto.getCreatedAt()).isEqualTo(created);
        assertThat(dto.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        TeacherDto dto1 = new TeacherDto(1L, "Delahaye", "Margot", created, updated);
        TeacherDto dto2 = new TeacherDto(1L, "Delahaye", "Margot", created, updated);
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void testToString() {
        TeacherDto dto = new TeacherDto();
        assertThat(dto.toString()).contains("TeacherDto");
    }

    @Test
    void testCanEqual() {
        TeacherDto dto = new TeacherDto();
        assertThat(dto.canEqual(new TeacherDto())).isTrue();
    }
} 