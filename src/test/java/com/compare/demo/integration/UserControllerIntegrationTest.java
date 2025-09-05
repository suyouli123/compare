package com.compare.demo.integration;

import com.compare.demo.controller.UserController;
import com.compare.demo.dto.UserDTO;
import com.compare.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void fullUserCRUDIntegrationTest() throws Exception {
        // Create test data
        UserDTO testUser = new UserDTO(1L, "integrationuser", "integration@example.com");
        List<UserDTO> userList = Arrays.asList(testUser);

        // Test GET all users
        when(userService.getAllUsers()).thenReturn(userList);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is("integrationuser")));

        // Test GET user by ID
        when(userService.getUserById(1L)).thenReturn(testUser);
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("integrationuser")));

        // Test GET user by username
        when(userService.getUserByUsername("integrationuser")).thenReturn(testUser);
        mockMvc.perform(get("/api/users/username/integrationuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("integration@example.com")));

        // Test POST create user
        UserDTO newUser = new UserDTO(null, "newuser", "newuser@example.com");
        UserDTO createdUser = new UserDTO(2L, "newuser", "newuser@example.com");
        when(userService.createUser(any(UserDTO.class))).thenReturn(createdUser);
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.username", is("newuser")));

        // Test PUT update user
        UserDTO updateUser = new UserDTO(1L, "updateduser", "updated@example.com");
        when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(updateUser);
        
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("updateduser")))
                .andExpect(jsonPath("$.email", is("updated@example.com")));

        // Test DELETE user
        when(userService.deleteUser(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void endpointErrorHandlingIntegrationTest() throws Exception {
        // Test 404 for non-existent user
        when(userService.getUserById(999L)).thenReturn(null);
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());

        // Test 404 for update non-existent user
        when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(null);
        UserDTO updateDTO = new UserDTO(999L, "test", "test@example.com");
        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());

        // Test 404 for delete non-existent user
        when(userService.deleteUser(999L)).thenReturn(false);
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound());
    }
}