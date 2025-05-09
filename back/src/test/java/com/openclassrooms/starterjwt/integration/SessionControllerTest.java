package com.openclassrooms.starterjwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void findAll_ShouldReturnSessions() throws Exception {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");
        session.setTeacher(teacher);
        session.setDate(new Date());
        
        when(sessionService.findAll()).thenReturn(Arrays.asList(session));

        // WHEN & THEN
        mockMvc.perform(get("/api/session")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Yoga Session"));
    }

    @Test
    @WithMockUser
    void findById_ShouldReturnSession() throws Exception {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");
        session.setTeacher(teacher);
        session.setDate(new Date());
        
        when(sessionService.getById(1L)).thenReturn(session);

        // WHEN & THEN
        mockMvc.perform(get("/api/session/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yoga Session"));
    }

    @Test
    @WithMockUser
    void create_ShouldReturnSession() throws Exception {
        SessionDto dto = new SessionDto();
        dto.setName("Yoga");
        dto.setDescription("Cours de yoga");
        dto.setTeacher_id(1L);
        dto.setDate(new java.util.Date());
        dto.setUsers(java.util.Collections.emptyList());
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga");
        session.setDescription("Cours de yoga");
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        session.setTeacher(teacher);
        when(sessionService.create(any(Session.class))).thenReturn(session);
        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yoga"));
    }

    @Test
    @WithMockUser
    void update_ShouldReturnSession() throws Exception {
        SessionDto dto = new SessionDto();
        dto.setName("Yoga");
        dto.setDescription("Cours de yoga");
        dto.setTeacher_id(1L);
        dto.setDate(new java.util.Date());
        dto.setUsers(java.util.Collections.emptyList());
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga");
        session.setDescription("Cours de yoga");
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        session.setTeacher(teacher);
        when(sessionService.update(eq(1L), any(Session.class))).thenReturn(session);
        mockMvc.perform(put("/api/session/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yoga"));
    }

    @Test
    @WithMockUser
    void update_ShouldReturnBadRequest_WhenIdIsInvalid() throws Exception {
        SessionDto dto = new SessionDto();
        mockMvc.perform(put("/api/session/abc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void delete_ShouldReturnOk_WhenSessionExists() throws Exception {
        Session session = new Session();
        session.setId(1L);
        when(sessionService.getById(1L)).thenReturn(session);
        doNothing().when(sessionService).delete(1L);
        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void delete_ShouldReturnNotFound_WhenSessionDoesNotExist() throws Exception {
        when(sessionService.getById(1L)).thenReturn(null);
        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void delete_ShouldReturnBadRequest_WhenIdIsInvalid() throws Exception {
        mockMvc.perform(delete("/api/session/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void participate_ShouldReturnOk() throws Exception {
        doNothing().when(sessionService).participate(1L, 2L);
        mockMvc.perform(post("/api/session/1/participate/2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void participate_ShouldReturnBadRequest_WhenIdIsInvalid() throws Exception {
        mockMvc.perform(post("/api/session/abc/participate/2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void noLongerParticipate_ShouldReturnOk() throws Exception {
        doNothing().when(sessionService).noLongerParticipate(1L, 2L);
        mockMvc.perform(delete("/api/session/1/participate/2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void noLongerParticipate_ShouldReturnBadRequest_WhenIdIsInvalid() throws Exception {
        mockMvc.perform(delete("/api/session/abc/participate/2"))
                .andExpect(status().isBadRequest());
    }
} 