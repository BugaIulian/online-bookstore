package group1.itschool.onlinebookstore.services.user;

import group1.itschool.onlinebookstore.models.dto.UserDTO;
import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> getUsers();

    UserDTO updateUserById(Long id, UserDTO userDTO);

    void deleteUserById(Long id);
}