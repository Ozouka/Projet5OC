package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
        user.setAdmin(true);
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isAdmin()).isTrue();
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "test@test.com", "Doe", "John", "password", true, now, now);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isAdmin()).isTrue();
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testPartialConstructor() {
        User user = new User("test@test.com", "Doe", "John", "password", true);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isAdmin()).isTrue();
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        User user1 = new User(1L, "test@test.com", "John", "Doe", "password", true, now, now);
        User user2 = new User(1L, "test@test.com", "John", "Doe", "password", true, now, now);
        User user3 = new User(2L, "other@test.com", "Jane", "Smith", "pass", false, now, now);
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
        assertThat(user1).isNotEqualTo(user3);
    }

    @Test
    void testToString() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
        user.setAdmin(true);
        String str = user.toString();
        assertThat(str).contains("test@test.com");
        assertThat(str).contains("John");
        assertThat(str).contains("Doe");
    }

    @Test
    void testCanEqual() {
        User user = new User();
        assertThat(user.canEqual(new User())).isTrue();
        assertThat(user.canEqual("not a User")).isFalse();
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
            .id(1L)
            .email("test@test.com")
            .firstName("John")
            .lastName("Doe")
            .password("password")
            .admin(true)
            .createdAt(now)
            .updatedAt(now)
            .build();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isAdmin()).isTrue();
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertThat(user).isNotNull();
    }

    @Test
    void testRequiredArgsConstructor() {
        User user = new User("test@test.com", "Doe", "John", "password", true);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isAdmin()).isTrue();
    }
} 