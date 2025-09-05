package com.compare.demo.controller;

import com.compare.demo.dto.UserDTO;
import com.compare.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerMockTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
        testUserDTO = new UserDTO(1L, "testuser", "test@example.com");
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() throws Exception {
        // Arrange
        List<UserDTO> users = Arrays.asList(testUserDTO);
        when(userService.getAllUsers()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("testuser")))
                .andExpect(jsonPath("$[0].email", is("test@example.com")));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_ShouldReturnUserWhenExists() throws Exception {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("testuser")))
                .andExpect(jsonPath("$.email", is("test@example.com")));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserById_ShouldReturnNotFoundWhenNotExists() throws Exception {
        // Arrange
        when(userService.getUserById(999L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(999L);
    }

    @Test
    void getUserByUsername_ShouldReturnUserWhenExists() throws Exception {
        // Arrange
        when(userService.getUserByUsername("testuser")).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(get("/api/users/username/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("testuser")))
                .andExpect(jsonPath("$.email", is("test@example.com")));

        verify(userService, times(1)).getUserByUsername("testuser");
    }

    @Test
    void getUserByUsername_ShouldReturnNotFoundWhenNotExists() throws Exception {
        // Arrange
        when(userService.getUserByUsername("nonexistent")).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/users/username/nonexistent"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserByUsername("nonexistent");
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        // Arrange
        UserDTO newUserDTO = new UserDTO(null, "newuser", "newuser@example.com");
        UserDTO createdUserDTO = new UserDTO(1L, "newuser", "newuser@example.com");
        
        when(userService.createUser(any(UserDTO.class))).thenReturn(createdUserDTO);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("newuser")))
                .andExpect(jsonPath("$.email", is("newuser@example.com")));

        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenInvalidData() throws Exception {
        // Arrange
        String invalidJson = "{ invalid json }";

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserWhenExists() throws Exception {
        // Arrange
        UserDTO updateDTO = new UserDTO(1L, "updateduser", "updated@example.com");
        when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(updateDTO);

        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("updateduser")))
                .andExpect(jsonPath("$.email", is("updated@example.com")));

        verify(userService, times(1)).updateUser(eq(1L), any(UserDTO.class));
    }

    @Test
    void updateUser_ShouldReturnNotFoundWhenNotExists() throws Exception {
        // Arrange
        UserDTO updateDTO = new UserDTO(999L, "updateduser", "updated@example.com");
        when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).updateUser(eq(999L), any(UserDTO.class));
    }

    @Test
    void deleteUser_ShouldReturnNoContentWhenExists() throws Exception {
        // Arrange
        when(userService.deleteUser(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void deleteUser_ShouldReturnNotFoundWhenNotExists() throws Exception {
        // Arrange
        when(userService.deleteUser(999L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).deleteUser(999L);
    }

    @Test
    void createUser_ShouldValidateRequiredFields() throws Exception {
        // Arrange
        UserDTO invalidUserDTO = new UserDTO(null, "", ""); // Empty username and email

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUserDTO)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }
}