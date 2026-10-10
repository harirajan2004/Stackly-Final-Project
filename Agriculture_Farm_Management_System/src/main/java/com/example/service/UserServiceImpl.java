package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {

        // Check if username already exists
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("Username '" + requestDto.getUsername() + "' is already taken!");
        }

        User user = userMapper.toEntity(requestDto);

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toResponseDto(user);
    }
}