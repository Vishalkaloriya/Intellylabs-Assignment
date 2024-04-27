package com.example.task.UserController;

import com.example.task.Dto.UserDTO;
import com.example.task.Dto.LoginDTO;
import com.example.task.Service.UserService;
import com.example.task.responce.LoginResponse;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/save")
    public ResponseEntity<?> saveUser(@Validated @RequestBody UserDTO userDTO) {
        // Check if user already exists
        if (userService.isUserExists(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("User already registered");
        }

        // Add user
        String id = userService.addUser(userDTO);
        return ResponseEntity.ok("User Registration Successful");
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        // Check if email and password are provided
        if (StringUtils.isBlank(loginDTO.getEmail()) || StringUtils.isBlank(loginDTO.getPassword())) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }

        LoginResponse loginResponse = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestParam String email) {
        userService.logoutUser(email);
        return ResponseEntity.ok("Logged out successfully");
    }
}
