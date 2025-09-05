package com.compare.demo.service;

import com.compare.demo.dto.UserDTO;
import com.compare.demo.entity.User;
import com.compare.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    public List<UserDTO> getAllUsers() {
        List<User> users = userMapper.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public UserDTO getUserById(Long id) {
        User user = userMapper.findById(id);
        return user != null ? convertToDTO(user) : null;
    }
    
    public UserDTO getUserByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user != null ? convertToDTO(user) : null;
    }
    
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        userMapper.insert(user);
        return convertToDTO(user);
    }
    
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userMapper.findById(id);
        if (existingUser == null) {
            return null;
        }
        
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setUpdatedAt(LocalDateTime.now());
        
        userMapper.update(existingUser);
        return convertToDTO(existingUser);
    }
    
    public boolean deleteUser(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            return false;
        }
        userMapper.deleteById(id);
        return true;
    }
    
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }
    
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}