package com.example.onlinebookstore.services.user;

import com.example.onlinebookstore.exceptions.user.UserNotFoundException;
import com.example.onlinebookstore.exceptions.user.UsernameAlreadyExistsException;
import com.example.onlinebookstore.models.dto.UserDTO;
import com.example.onlinebookstore.models.entities.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.onlinebookstore.models.dto.auth.LoginRequestDTO;
import com.example.onlinebookstore.models.dto.auth.RegisterRequestDTO;
import com.example.onlinebookstore.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(ObjectMapper objectMapper, UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public RegisterRequestDTO registerUser(RegisterRequestDTO registerRequestDTO) {
        try {
            checkIfUserIsAlreadyRegistered(registerRequestDTO);
            UserEntity userToBeRegistered = objectMapper.convertValue(registerRequestDTO, UserEntity.class);
            userToBeRegistered.setAccountCreationDate(LocalDate.now());
            userToBeRegistered.setPassword(encodePassword(registerRequestDTO.getPassword()));
            UserEntity userSaved = userRepository.save(userToBeRegistered);
            return objectMapper.convertValue(userSaved, RegisterRequestDTO.class);
        } catch (UsernameAlreadyExistsException e) {
            log.info("Username already in the table");
            throw new RuntimeException(e);
        }
    }

    /** This is due to be done*/

    @Override
    public LoginRequestDTO userLogin(LoginRequestDTO loginRequestDTO) {
        return null;
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
        UserEntity editedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User to be erased can't be found with the id: " + id));
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
                throw new UsernameAlreadyExistsException("Username already registered.");
            }
            UserEntity existingUserEmail = userRepository.findByEmail(userEmail);
            if (existingUserEmail != null) {
                throw new UsernameAlreadyExistsException("Email already registered");
            }
    }
}