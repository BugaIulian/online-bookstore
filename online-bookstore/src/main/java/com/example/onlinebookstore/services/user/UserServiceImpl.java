package com.example.onlinebookstore.services.user;

import com.example.onlinebookstore.exceptions.user.UsersCredentialsExceptions;
import com.example.onlinebookstore.exceptions.user.UserCreationException;
import com.example.onlinebookstore.exceptions.user.UserNotFoundException;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public RegisterRequestDTO registerUser(RegisterRequestDTO registerRequestDTO) {
        try {
            checkIfUserIsAlreadyRegistered(registerRequestDTO);
            UserEntity userToBeRegistered = objectMapper.convertValue(registerRequestDTO, UserEntity.class);
            userToBeRegistered.setAccountCreationDate(LocalDate.now());
            userToBeRegistered.setPassword(encodePassword(registerRequestDTO.getPassword()));
            UserEntity userSaved = userRepository.save(userToBeRegistered);
            emailService.sendRegistrationEmail(userSaved.getEmail(), userSaved.getUsername());
            return objectMapper.convertValue(userSaved, RegisterRequestDTO.class);
        } catch (UserCreationException e) {
            throw new UserCreationException("Username already assigned");
        }
    }


    @Override
    public LoginRequestDTO userLogin(LoginRequestDTO loginRequestDTO) {
        UserEntity user = userRepository.findByUsername(loginRequestDTO.getUsername());
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        boolean passwordMatch = checkPassword(loginRequestDTO, user);
        boolean usernameMatch = checkUsername(loginRequestDTO,user);

        if (passwordMatch && usernameMatch) {
            return loginRequestDTO;
        } else {
            throw new UsersCredentialsExceptions("Incorrect Credentials please try again");
        }
    }

    private boolean checkUsername(LoginRequestDTO loginRequestDTO, UserEntity user) {
        return loginRequestDTO.getUsername().equals(user.getUsername());
    }

    private boolean checkPassword(LoginRequestDTO loginRequestDTO, UserEntity user) {
        return new BCryptPasswordEncoder().matches(loginRequestDTO.getPassword(), user.getPassword());
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        userEntities.forEach(userEntity -> userDTOS.add(objectMapper.convertValue(userEntity, UserDTO.class)));
        return userDTOS;
    }

    @Override
    public UserDTO createUserProfile(Long id, UserDTO userDTO) {
        UserEntity editedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        updateUserFields(userDTO, editedUser);
        UserEntity editedUserSaved = userRepository.save(editedUser);
        return objectMapper.convertValue(editedUserSaved, UserDTO.class);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User to be erased with the id: " + id + " can't be found");
        }
        userRepository.deleteById(id);
    }

    private void updateUserFields(UserDTO userDTO, UserEntity editedUser) {
        editedUser.setUsername(userDTO.getUsername());
        editedUser.setFullName(userDTO.getFullName());
        editedUser.setEmail(userDTO.getEmail());
        editedUser.setInterests(userDTO.getInterests());
        editedUser.setDateOfBirth(userDTO.getDateOfBirth());
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private void checkIfUserIsAlreadyRegistered(RegisterRequestDTO registerRequestDTO) {

            String username = registerRequestDTO.getUsername();
            String userEmail = registerRequestDTO.getEmail();
            UserEntity existingUserUsername = userRepository.findByUsername(username);
            if (existingUserUsername != null) {
                throw new UserCreationException("Username already registered.");
            }
            UserEntity existingUserEmail = userRepository.findByEmail(userEmail);
            if (existingUserEmail != null) {
                throw new UserCreationException("Email already registered");
            }
    }
}