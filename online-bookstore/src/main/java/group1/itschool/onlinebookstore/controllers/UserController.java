package group1.itschool.onlinebookstore.controllers;

import group1.itschool.onlinebookstore.models.dto.Auth.RegisterRequestDTO;
import group1.itschool.onlinebookstore.models.dto.UserDTO;
import group1.itschool.onlinebookstore.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<RegisterRequestDTO> registerUser(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(userService.registerUser(registerRequestDTO));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> createUserProfile(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUserProfile(id, userDTO));
    }

    /**This API endpoint should be moved to the admin controller, more exactly the BookController, there is no logic reason to have it here*/

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
    }
}