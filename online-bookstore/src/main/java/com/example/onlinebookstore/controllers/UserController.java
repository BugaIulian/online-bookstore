package com.example.onlinebookstore.controllers;

import com.example.onlinebookstore.models.dto.OrderDTO;
import com.example.onlinebookstore.models.dto.UserDTO;
import com.example.onlinebookstore.models.dto.auth.LoginRequestDTO;
import com.example.onlinebookstore.models.dto.auth.RegisterRequestDTO;
import com.example.onlinebookstore.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(registerRequestDTO));
    }

    @PostMapping("users/login")
    public ResponseEntity<LoginRequestDTO> userLogin(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userService.userLogin(loginRequestDTO));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> createUserProfile(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserProfile(id, userDTO));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("admin/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("users/order/{userId}")
    public ResponseEntity<OrderDTO> createOrderById(@PathVariable Long userId, @RequestBody @Valid OrderDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createOrder(userId, orderDTO));
    }
}