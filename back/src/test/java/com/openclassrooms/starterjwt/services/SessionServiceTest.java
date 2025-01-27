package com.openclassrooms.starterjwt.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.exception.BadRequestException;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void getById_ShouldReturnSession_WhenSessionExists() {
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");
        session.setDate(new Date());
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        // WHEN
        Session result = sessionService.getById(1L);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Yoga Session");
    }

    @Test
    void getById_ShouldReturnNull_WhenSessionDoesNotExist() {
        // GIVEN
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN
        Session result = sessionService.getById(1L);

        // THEN
        assertThat(result).isNull();
    }

    @Test
    void findAll_ShouldReturnAllSessions() {
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");
        when(sessionRepository.findAll()).thenReturn(Arrays.asList(session));

        // WHEN
        List<Session> result = sessionService.findAll();

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Yoga Session");
    }

    @Test
    void create_ShouldReturnSavedSession() {
        // GIVEN
        Session session = new Session();
        session.setName("New Session");
        
        Session savedSession = new Session();
        savedSession.setId(1L);
        savedSession.setName("New Session");
        
        when(sessionRepository.save(session)).thenReturn(savedSession);

        // WHEN
        Session result = sessionService.create(session);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("New Session");
    }

    @Test
    void delete_ShouldCallRepository() {
        // GIVEN
        Long id = 1L;

        // WHEN
        sessionService.delete(id);

        // THEN
        verify(sessionRepository).deleteById(id);
    }

    @Test
    void participate_ShouldAddUserToSession() {
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());
        
        User user = new User();
        user.setId(1L);
        
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // WHEN
        sessionService.participate(1L, 1L);

        // THEN
        verify(sessionRepository).save(session);
        assertThat(session.getUsers()).contains(user);
    }

    @Test
    void participate_ShouldThrowNotFoundException_WhenSessionNotFound() {
        // GIVEN
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThatThrownBy(() -> sessionService.participate(1L, 1L))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void noLongerParticipate_ShouldRemoveUserFromSession() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        
        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>(Arrays.asList(user)));
        
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // WHEN
        sessionService.noLongerParticipate(1L, 1L);

        // THEN
        verify(sessionRepository).save(session);
        assertThat(session.getUsers()).doesNotContain(user);
    }
} 