package com.billsplanning.backend.controller;

import com.billsplanning.backend.DTO.Auth.UserRegisterRequestDTO;
import com.billsplanning.backend.entity.User;
import com.billsplanning.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper; 

    @Test
    @WithMockUser
    @DisplayName("It should register the user and return 200 OK with user data")
    void shouldRegisterUserWith200AndReturnUserData() throws Exception {
        UserRegisterRequestDTO request = new UserRegisterRequestDTO("test", "test@test.com", "123456");
        
        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setUsername("test");
        savedUser.setEmail("test@test.com");

        when(userService.registerUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/users/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }
}