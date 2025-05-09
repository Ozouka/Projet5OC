package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class TeacherTest {
    @Test
    void testGettersAndSetters() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Margot");
        teacher.setLastName("Delahaye");
        LocalDateTime now = LocalDateTime.now();
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);
        assertThat(teacher.getId()).isEqualTo(1L);
        assertThat(teacher.getFirstName()).isEqualTo("Margot");
        assertThat(teacher.getLastName()).isEqualTo("Delahaye");
        assertThat(teacher.getCreatedAt()).isEqualTo(now);
        assertThat(teacher.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testFullConstructor() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        Teacher teacher = new Teacher(1L, "Delahaye", "Margot", created, updated);
        assertThat(teacher.getId()).isEqualTo(1L);
        assertThat(teacher.getLastName()).isEqualTo("Delahaye");
        assertThat(teacher.getFirstName()).isEqualTo("Margot");
        assertThat(teacher.getCreatedAt()).isEqualTo(created);
        assertThat(teacher.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        Teacher t1 = new Teacher(1L, "Delahaye", "Margot", created, updated);
        Teacher t2 = new Teacher(1L, "Delahaye", "Margot", created, updated);
        assertThat(t1).isEqualTo(t2);
        assertThat(t1.hashCode()).isEqualTo(t2.hashCode());
    }

    @Test
    void testToString() {
        Teacher teacher = new Teacher();
        assertThat(teacher.toString()).contains("Teacher");
    }

    @Test
    void testCanEqual() {
        Teacher teacher = new Teacher();
        assertThat(teacher.canEqual(new Teacher())).isTrue();
    }

    @Test
    void testBuilder() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        Teacher teacher = Teacher.builder()
                .id(1L)
                .lastName("Delahaye")
                .firstName("Margot")
                .createdAt(created)
                .updatedAt(updated)
                .build();
        assertThat(teacher.getId()).isEqualTo(1L);
        assertThat(teacher.getLastName()).isEqualTo("Delahaye");
        assertThat(teacher.getFirstName()).isEqualTo("Margot");
        assertThat(teacher.getCreatedAt()).isEqualTo(created);
        assertThat(teacher.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void testTeacherBuilder() {
        Teacher.TeacherBuilder builder = Teacher.builder();
        assertThat(builder).isNotNull();
        Teacher teacher = builder.lastName("Test").build();
        assertThat(teacher).isNotNull();
        assertThat(teacher.getLastName()).isEqualTo("Test");
    }
} 