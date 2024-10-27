package com.ijse.cmjd106.posSystem.service;

import org.springframework.stereotype.Service;

import com.ijse.cmjd106.posSystem.dto.LoginRequest;
import com.ijse.cmjd106.posSystem.dto.UserResponse;


@Service
public interface UserService {
UserResponse createUser(LoginRequest loginRequest);
}
