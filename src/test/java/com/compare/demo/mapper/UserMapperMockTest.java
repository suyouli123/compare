package com.compare.demo.mapper;

import com.compare.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMapperMockTest {

    @Mock
    private UserMapper userMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "test@example.com");
        testUser.setId(1L);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(testUser);
        when(userMapper.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userMapper.findAll();

        // Assert
        assertNotNull(actualUsers);
        assertEquals(1, actualUsers.size());
        assertEquals(testUser.getId(), actualUsers.get(0).getId());
        assertEquals(testUser.getUsername(), actualUsers.get(0).getUsername());
        assertEquals(testUser.getEmail(), actualUsers.get(0).getEmail());
        verify(userMapper, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnUserWhenExists() {
        // Arrange
        when(userMapper.findById(1L)).thenReturn(testUser);

        // Act
        User result = userMapper.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userMapper, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnNullWhenNotExists() {
        // Arrange
        when(userMapper.findById(999L)).thenReturn(null);

        // Act
        User result = userMapper.findById(999L);

        // Assert
        assertNull(result);
        verify(userMapper, times(1)).findById(999L);
    }

    @Test
    void findByUsername_ShouldReturnUserWhenExists() {
        // Arrange
        when(userMapper.findByUsername("testuser")).thenReturn(testUser);

        // Act
        User result = userMapper.findByUsername("testuser");

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userMapper, times(1)).findByUsername("testuser");
    }

    @Test
    void findByUsername_ShouldReturnNullWhenNotExists() {
        // Arrange
        when(userMapper.findByUsername("nonexistent")).thenReturn(null);

        // Act
        User result = userMapper.findByUsername("nonexistent");

        // Assert
        assertNull(result);
        verify(userMapper, times(1)).findByUsername("nonexistent");
    }

    @Test
    void insert_ShouldReturnGeneratedId() {
        // Arrange
        when(userMapper.insert(any(User.class))).thenReturn(1);

        // Act
        int result = userMapper.insert(testUser);

        // Assert
        assertEquals(1, result);
        verify(userMapper, times(1)).insert(any(User.class));
    }

    @Test
    void insert_ShouldCallMapperWithCorrectData() {
        // Arrange
        User newUser = new User("newuser", "newuser@example.com");
        when(userMapper.insert(any(User.class))).thenReturn(1);

        // Act
        userMapper.insert(newUser);

        // Assert
        verify(userMapper, times(1)).insert(argThat(user -> 
            user.getUsername().equals("newuser") && 
            user.getEmail().equals("newuser@example.com")
        ));
    }

    @Test
    void update_ShouldReturnSuccessCount() {
        // Arrange
        when(userMapper.update(any(User.class))).thenReturn(1);

        // Act
        int result = userMapper.update(testUser);

        // Assert
        assertEquals(1, result);
        verify(userMapper, times(1)).update(any(User.class));
    }

    @Test
    void update_ShouldCallMapperWithUpdatedData() {
        // Arrange
        User updatedUser = new User("updateduser", "updated@example.com");
        updatedUser.setId(1L);
        when(userMapper.update(any(User.class))).thenReturn(1);

        // Act
        userMapper.update(updatedUser);

        // Assert
        verify(userMapper, times(1)).update(argThat(user -> 
            user.getId().equals(1L) && 
            user.getUsername().equals("updateduser") && 
            user.getEmail().equals("updated@example.com")
        ));
    }

    @Test
    void deleteById_ShouldReturnSuccessCount() {
        // Arrange
        when(userMapper.deleteById(1L)).thenReturn(1);

        // Act
        int result = userMapper.deleteById(1L);

        // Assert
        assertEquals(1, result);
        verify(userMapper, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ShouldCallMapperWithCorrectId() {
        // Arrange
        when(userMapper.deleteById(anyLong())).thenReturn(1);

        // Act
        userMapper.deleteById(123L);

        // Assert
        verify(userMapper, times(1)).deleteById(123L);
    }

    @Test
    void deleteById_ShouldHandleNonexistentUser() {
        // Arrange
        when(userMapper.deleteById(999L)).thenReturn(0);

        // Act
        int result = userMapper.deleteById(999L);

        // Assert
        assertEquals(0, result);
        verify(userMapper, times(1)).deleteById(999L);
    }
}