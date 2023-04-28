package group1.itschool.onlinebookstore.services.user;

import group1.itschool.onlinebookstore.models.dto.Auth.LoginRequestDTO;
import group1.itschool.onlinebookstore.models.dto.Auth.RegisterRequestDTO;
import group1.itschool.onlinebookstore.models.dto.UserDTO;

import java.util.List;

public interface UserService {

    RegisterRequestDTO registerUser(RegisterRequestDTO registerRequestDTO);

    LoginRequestDTO userLogin(LoginRequestDTO loginRequestDTO);

    List<UserDTO> getUsers();

    UserDTO createUserProfile(Long id, UserDTO userDTO);

    void deleteUserById(Long id);
}