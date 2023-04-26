package group1.itschool.onlinebookstore.services.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import group1.itschool.onlinebookstore.models.dto.UserDTO;
import group1.itschool.onlinebookstore.models.entities.UserEntity;
import group1.itschool.onlinebookstore.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        UserDTO userDTOReturned = objectMapper.convertValue(userSaved, UserDTO.class);;
        userDTOReturned.setAccountCreationDate(LocalDate.now());
        return userDTOReturned;
    }

    @Override
    public List<UserDTO> getUsers() {
        return null;
    }

    @Override
    public UserDTO updateUserById(Long id, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }
}