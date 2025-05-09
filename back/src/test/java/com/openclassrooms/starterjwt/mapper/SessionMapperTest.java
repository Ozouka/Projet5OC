package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

public class SessionMapperTest {
    private SessionMapperImpl mapper;
    private Teacher teacher;
    private User user;

    @BeforeEach
    void setUp() {
        mapper = new SessionMapperImpl();
        teacher = new Teacher();
        teacher.setId(2L);
        user = new User();
        user.setId(1L);
        // Mock les services si besoin
        mapper.teacherService = Mockito.mock(com.openclassrooms.starterjwt.services.TeacherService.class);
        mapper.userService = Mockito.mock(com.openclassrooms.starterjwt.services.UserService.class);
        Mockito.when(mapper.teacherService.findById(anyLong())).thenReturn(teacher);
        Mockito.when(mapper.userService.findById(anyLong())).thenReturn(user);
    }

    @Test
    void testToEntity() {
        SessionDto dto = new SessionDto(1L, "Yoga", new Date(), 2L, "Cours de yoga", Arrays.asList(1L), LocalDateTime.now(), LocalDateTime.now());
        Session session = mapper.toEntity(dto);
        assertThat(session).isNotNull();
        assertThat(session.getId()).isEqualTo(dto.getId());
        assertThat(session.getName()).isEqualTo(dto.getName());
        assertThat(session.getDescription()).isEqualTo(dto.getDescription());
        assertThat(session.getTeacher()).isNotNull();
        assertThat(session.getUsers()).isNotEmpty();
    }

    @Test
    void testToDto() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga");
        session.setDescription("Cours de yoga");
        session.setTeacher(teacher);
        session.setUsers(Collections.singletonList(user));
        session.setDate(new Date());
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());
        SessionDto dto = mapper.toDto(session);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(session.getId());
        assertThat(dto.getName()).isEqualTo(session.getName());
        assertThat(dto.getDescription()).isEqualTo(session.getDescription());
        assertThat(dto.getTeacher_id()).isEqualTo(teacher.getId());
        assertThat(dto.getUsers()).containsExactly(user.getId());
    }

    @Test
    void testToEntityList() {
        SessionDto dto = new SessionDto(1L, "Yoga", new Date(), 2L, "Cours de yoga", Arrays.asList(1L), LocalDateTime.now(), LocalDateTime.now());
        List<Session> sessions = mapper.toEntity(Arrays.asList(dto));
        assertThat(sessions).hasSize(1);
    }

    @Test
    void testToDtoList() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga");
        session.setDescription("Cours de yoga");
        session.setTeacher(teacher);
        session.setUsers(Collections.singletonList(user));
        session.setDate(new Date());
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());
        List<SessionDto> dtos = mapper.toDto(Arrays.asList(session));
        assertThat(dtos).hasSize(1);
    }
} 