package com.example.onlinebookstore.services.user;

import com.example.onlinebookstore.exceptions.user.UserRegisterException;
import com.example.onlinebookstore.exceptions.user.UserNotFoundException;
import com.example.onlinebookstore.exceptions.user.UserPasswordExceptions;
import com.example.onlinebookstore.models.dto.UserDTO;
import com.example.onlinebookstore.models.dto.auth.LoginRequestDTO;
import com.example.onlinebookstore.models.dto.auth.RegisterRequestDTO;
import com.example.onlinebookstore.models.entities.UserEntity;
import com.example.onlinebookstore.repositories.UserRepository;
import com.example.onlinebookstore.services.email.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserServiceImpl(ObjectMapper objectMapper, UserRepository userRepository, EmailService emailService) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public RegisterRequestDTO registerUser(RegisterRequestDTO registerRequestDTO) {

        UserEntity newUser = userRepository.findByUsername(registerRequestDTO.getUsername());
        checkNewUserForUsernameAndEmailForDuplicates(registerRequestDTO, newUser);
        UserEntity userSaved = saveNewUserToUsersTableAndEncodePassword(registerRequestDTO);
        emailService.sendRegistrationEmail(userSaved.getEmail(), userSaved.getUsername());
        return objectMapper.convertValue(userSaved, RegisterRequestDTO.class);
    }

    @Override
    public LoginRequestDTO userLogin(LoginRequestDTO loginRequestDTO) {

        UserEntity userLogin = userRepository.findByUsername(loginRequestDTO.getUsername());
        if (userLogin == null) {
            throw new UserNotFoundException("User not found");
        }

        if (checkUserPasswordForLogin(loginRequestDTO, userLogin) && checkUsernameForLogin(loginRequestDTO, userLogin)) {
            return loginRequestDTO;
        } else {
            throw new UserPasswordExceptions("Incorrect password, please try again");
        }
    }

    @Override
    @Transactional
    public UserDTO createUserProfile(Long id, UserDTO userDTO) {
        UserEntity userProfile = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        updateUserProfile(userDTO, userProfile);
        userProfile = userRepository.save(userProfile);
        return objectMapper.convertValue(userProfile, UserDTO.class);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User to be erased with the id:" + id + "can't be found"));
        userRepository.deleteById(id);
    }

    private void updateUserProfile(UserDTO userDTO, UserEntity editedUser) {
        editedUser.setUsername(userDTO.getUsername());
        editedUser.setFullName(userDTO.getFullName());
        editedUser.setEmail(userDTO.getEmail());
        editedUser.setInterests(userDTO.getInterests());
        editedUser.setDateOfBirth(userDTO.getDateOfBirth());
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private void checkNewUserForUsernameAndEmailForDuplicates(RegisterRequestDTO registerRequestDTO, UserEntity existingUserUsername) {
        if (existingUserUsername != null) {
            throw new UserRegisterException("Username already registered.");
        }
        UserEntity existingUserEmail = userRepository.findByEmail(registerRequestDTO.getEmail());
        if (existingUserEmail != null) {
            throw new UserRegisterException("Email already registered.");
        }
    }

    private UserEntity saveNewUserToUsersTableAndEncodePassword(RegisterRequestDTO registerRequestDTO) {
        UserEntity newUser;
        newUser = objectMapper.convertValue(registerRequestDTO, UserEntity.class);
        newUser.setAccountCreationDate(LocalDate.now());
        newUser.setPassword(encodePassword(registerRequestDTO.getPassword()));
        return userRepository.save(newUser);
    }

    private boolean checkUsernameForLogin(LoginRequestDTO loginRequestDTO, UserEntity user) {
        return loginRequestDTO.getUsername().equals(user.getUsername());
    }

    private boolean checkUserPasswordForLogin(LoginRequestDTO loginRequestDTO, UserEntity user) {
        return new BCryptPasswordEncoder().matches(loginRequestDTO.getPassword(), user.getPassword());
    }
}