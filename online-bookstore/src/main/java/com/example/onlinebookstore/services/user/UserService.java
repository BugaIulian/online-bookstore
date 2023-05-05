package com.example.onlinebookstore.services.user;

import com.example.onlinebookstore.models.dto.UserDTO;
import com.example.onlinebookstore.models.dto.auth.LoginRequestDTO;
import com.example.onlinebookstore.models.dto.auth.RegisterRequestDTO;

import java.util.List;

public interface UserService {

    RegisterRequestDTO registerUser(RegisterRequestDTO registerRequestDTO);

    LoginRequestDTO userLogin(LoginRequestDTO loginRequestDTO);

    UserDTO createUserProfile(Long id, UserDTO userDTO);

    void deleteUserById(Long id);
}