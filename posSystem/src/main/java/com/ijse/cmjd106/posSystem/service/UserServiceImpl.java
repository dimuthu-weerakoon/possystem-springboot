package com.ijse.cmjd106.posSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.LoginRequest;
import com.ijse.cmjd106.posSystem.dto.UserResponse;
import com.ijse.cmjd106.posSystem.model.User;
import com.ijse.cmjd106.posSystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(LoginRequest loginRequest) {
        User user = User.builder()
                .username(loginRequest.getUsername())
                .password(passwordEncoder.encode(loginRequest.getPassword()))
                .build();

        User createdUser = userRepository.save(user);

        return mapToUserResponse(createdUser);

    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

}
