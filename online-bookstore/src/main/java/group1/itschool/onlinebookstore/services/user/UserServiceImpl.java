package group1.itschool.onlinebookstore.services.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import group1.itschool.onlinebookstore.models.dto.UserDTO;
import group1.itschool.onlinebookstore.models.entities.UserEntity;
import group1.itschool.onlinebookstore.repositories.UserRepository;
import group1.itschool.onlinebookstore.util.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(ObjectMapper objectMapper, UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userToBeSaved = objectMapper.convertValue(userDTO, UserEntity.class);
        userToBeSaved.setAccountCreationDate(LocalDate.now());
        UserEntity userSaved = userRepository.save(userToBeSaved);
        UserDTO userDTOReturned = objectMapper.convertValue(userSaved, UserDTO.class);
        userDTOReturned.setAccountCreationDate(LocalDate.now());
        return userDTOReturned;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        userEntities.forEach(userEntity -> {
            userDTOS.add(objectMapper.convertValue(userEntity, UserDTO.class));
        });
        return userDTOS;
    }

    @Override
    public UserDTO updateUserById(Long id, UserDTO userDTO) {
        UserEntity editedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        updateUserFields(userDTO, editedUser);
        UserEntity editedUserSaved = userRepository.save(editedUser);
        return objectMapper.convertValue(editedUserSaved, UserDTO.class);
    }


    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    private void updateUserFields(UserDTO userDTO, UserEntity editedUser) {
        editedUser.setUsername(userDTO.getUsername());
        editedUser.setEmail(userDTO.getEmail());
        editedUser.setInterests(userDTO.getInterests());
        editedUser.setDateOfBirth(userDTO.getDateOfBirth());
    }
}