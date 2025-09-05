package com.compare.demo.service;

import com.compare.demo.dto.UserDTO;
import com.compare.demo.entity.User;
import com.compare.demo.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceMockTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "test@example.com");
        testUser.setId(1L);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());

        testUserDTO = new UserDTO(1L, "testuser", "test@example.com");
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(testUser);
        when(userMapper.findAll()).thenReturn(users);

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUserDTO.getUsername(), result.get(0).getUsername());
        assertEquals(testUserDTO.getEmail(), result.get(0).getEmail());
        verify(userMapper, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUserWhenExists() {
        // Arrange
        when(userMapper.findById(1L)).thenReturn(testUser);

        // Act
        UserDTO result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testUserDTO.getId(), result.getId());
        assertEquals(testUserDTO.getUsername(), result.getUsername());
        assertEquals(testUserDTO.getEmail(), result.getEmail());
        verify(userMapper, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldReturnNullWhenNotExists() {
        // Arrange
        when(userMapper.findById(1L)).thenReturn(null);

        // Act
        UserDTO result = userService.getUserById(1L);

        // Assert
        assertNull(result);
        verify(userMapper, times(1)).findById(1L);
    }

    @Test
    void getUserByUsername_ShouldReturnUserWhenExists() {
        // Arrange
        when(userMapper.findByUsername("testuser")).thenReturn(testUser);

        // Act
        UserDTO result = userService.getUserByUsername("testuser");

        // Assert
        assertNotNull(result);
        assertEquals(testUserDTO.getUsername(), result.getUsername());
        assertEquals(testUserDTO.getEmail(), result.getEmail());
        verify(userMapper, times(1)).findByUsername("testuser");
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        // Arrange
        when(userMapper.insert(any(User.class))).thenReturn(1);

        // Act
        UserDTO result = userService.createUser(testUserDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testUserDTO.getUsername(), result.getUsername());
        assertEquals(testUserDTO.getEmail(), result.getEmail());
        verify(userMapper, times(1)).insert(any(User.class));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserWhenExists() {
        // Arrange
        UserDTO updatedDTO = new UserDTO(1L, "updateduser", "updated@example.com");
        when(userMapper.findById(1L)).thenReturn(testUser);
        when(userMapper.update(any(User.class))).thenReturn(1);

        // Act
        UserDTO result = userService.updateUser(1L, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedDTO.getUsername(), result.getUsername());
        assertEquals(updatedDTO.getEmail(), result.getEmail());
        verify(userMapper, times(1)).findById(1L);
        verify(userMapper, times(1)).update(any(User.class));
    }

    @Test
    void updateUser_ShouldReturnNullWhenNotExists() {
        // Arrange
        when(userMapper.findById(1L)).thenReturn(null);

        // Act
        UserDTO result = userService.updateUser(1L, testUserDTO);

        // Assert
        assertNull(result);
        verify(userMapper, times(1)).findById(1L);
        verify(userMapper, never()).update(any(User.class));
    }

    @Test
    void deleteUser_ShouldReturnTrueWhenExists() {
        // Arrange
        when(userMapper.findById(1L)).thenReturn(testUser);
        when(userMapper.deleteById(1L)).thenReturn(1);

        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertTrue(result);
        verify(userMapper, times(1)).findById(1L);
        verify(userMapper, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_ShouldReturnFalseWhenNotExists() {
        // Arrange
        when(userMapper.findById(1L)).thenReturn(null);

        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertFalse(result);
        verify(userMapper, times(1)).findById(1L);
        verify(userMapper, never()).deleteById(1L);
    }
}