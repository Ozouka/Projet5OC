package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class TeacherMapperTest {
    private final TeacherMapperImpl mapper = new TeacherMapperImpl();

    @Test
    void testToDto() {
        Teacher teacher = new Teacher(1L, "Delahaye", "Margot", LocalDateTime.now(), LocalDateTime.now());
        TeacherDto dto = mapper.toDto(teacher);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(teacher.getId());
        assertThat(dto.getFirstName()).isEqualTo(teacher.getFirstName());
        assertThat(dto.getLastName()).isEqualTo(teacher.getLastName());
    }

    @Test
    void testToEntity() {
        TeacherDto dto = new TeacherDto(1L, "Delahaye", "Margot", LocalDateTime.now(), LocalDateTime.now());
        Teacher teacher = mapper.toEntity(dto);
        assertThat(teacher).isNotNull();
        assertThat(teacher.getId()).isEqualTo(dto.getId());
        assertThat(teacher.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(teacher.getLastName()).isEqualTo(dto.getLastName());
    }
} 