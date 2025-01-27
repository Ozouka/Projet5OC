package com.openclassrooms.starterjwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    @WithMockUser
    void findAll_ShouldReturnTeachers() throws Exception {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Toto");
        teacher.setLastName("Bard");
        
        when(teacherService.findAll()).thenReturn(Arrays.asList(teacher));

        // WHEN & THEN
        mockMvc.perform(get("/api/teacher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Toto"))
                .andExpect(jsonPath("$[0].lastName").value("Bard"));
    }

    @Test
    @WithMockUser
    void findById_ShouldReturnTeacher() throws Exception {
        // GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Toto");
        teacher.setLastName("Bard");
        
        when(teacherService.findById(1L)).thenReturn(teacher);

        // WHEN & THEN
        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Toto"))
                .andExpect(jsonPath("$.lastName").value("Bard"));
    }
} 