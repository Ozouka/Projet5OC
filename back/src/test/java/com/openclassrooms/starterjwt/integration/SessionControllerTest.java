package com.openclassrooms.starterjwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

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

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

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
} 