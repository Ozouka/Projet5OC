package com.openclassrooms.starterjwt.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        // GIVEN
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@test.com");
        user.setLastName("Snow");
        user.setFirstName("John");
        user.setPassword("test1234");
        when(userService.findById(userId)).thenReturn(user);
        when(jwtUtils.validateJwtToken("valid-token")).thenReturn(true);

        // WHEN & THEN
        mockMvc.perform(get("/api/user/" + userId)
                .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.lastName").value("Snow"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @WithMockUser
    void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        // GIVEN
        Long userId = 999L;
        when(userService.findById(userId)).thenReturn(null);
        when(jwtUtils.validateJwtToken("valid-token")).thenReturn(true);

        // WHEN & THEN
        mockMvc.perform(get("/api/user/" + userId)
                .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isNotFound());
    }
} 