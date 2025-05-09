package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {
    @Test
    void testGettersAndSetters() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga");
        session.setDescription("Cours de yoga");
        Teacher teacher = new Teacher();
        session.setTeacher(teacher);
        LocalDateTime now = LocalDateTime.now();
        session.setCreatedAt(now);
        session.setUpdatedAt(now);
        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("Yoga");
        assertThat(session.getDescription()).isEqualTo("Cours de yoga");
        assertThat(session.getTeacher()).isEqualTo(teacher);
        assertThat(session.getCreatedAt()).isEqualTo(now);
        assertThat(session.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testFullConstructor() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        Teacher teacher = new Teacher();
        Session session = new Session(1L, "Yoga", null, "Cours de yoga", teacher, null, created, updated);
        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("Yoga");
        assertThat(session.getDescription()).isEqualTo("Cours de yoga");
        assertThat(session.getTeacher()).isEqualTo(teacher);
        assertThat(session.getCreatedAt()).isEqualTo(created);
        assertThat(session.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        Teacher teacher = new Teacher();
        Session s1 = new Session(1L, "Yoga", null, "Cours de yoga", teacher, null, created, updated);
        Session s2 = new Session(1L, "Yoga", null, "Cours de yoga", teacher, null, created, updated);
        assertThat(s1).isEqualTo(s2);
        assertThat(s1.hashCode()).isEqualTo(s2.hashCode());
    }

    @Test
    void testToString() {
        Session session = new Session();
        assertThat(session.toString()).contains("Session");
    }

    @Test
    void testCanEqual() {
        Session session = new Session();
        assertThat(session.canEqual(new Session())).isTrue();
    }

    @Test
    void testBuilder() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        Teacher teacher = new Teacher();
        Session session = Session.builder()
                .id(1L)
                .name("Yoga")
                .createdAt(created)
                .updatedAt(updated)
                .teacher(teacher)
                .description("Cours de yoga")
                .build();
        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("Yoga");
        assertThat(session.getDescription()).isEqualTo("Cours de yoga");
        assertThat(session.getTeacher()).isEqualTo(teacher);
        assertThat(session.getCreatedAt()).isEqualTo(created);
        assertThat(session.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void testSessionBuilder() {
        Session.SessionBuilder builder = Session.builder();
        assertThat(builder).isNotNull();
        // On vérifie que le builder retourne bien un objet Session
        Session session = builder.name("Test").build();
        assertThat(session).isNotNull();
        assertThat(session.getName()).isEqualTo("Test");
    }
} 