package com.example.task.Service.impl;

import com.example.task.Dto.UserDTO;
import com.example.task.Dto.LoginDTO;
import com.example.task.Entity.User;
import com.example.task.Repo.UserRepo;
import com.example.task.Service.UserService;
import com.example.task.responce.LoginResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession httpSession; // Autowire HttpSession

    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUserId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword())
        );
        userRepo.save(user);
        return user.getUsername();
    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        User user = userRepo.findByEmail(loginDTO.getEmail());
        if (user != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                // Set up the user's session or authentication state
                httpSession.setAttribute("userEmail", user.getEmail());
                return new LoginResponse("Login Success", true);
            } else {
                return new LoginResponse("Password Not Match", false);
            }
        } else {
            return new LoginResponse("Email not exists", false);
        }

    }
    @Override
    public boolean isUserExists(String email) {
        User user = userRepo.findByEmail(email);
        return user != null;
    }

    // Add logoutUser method implementation here
    @Override
    public void logoutUser(String email) {
        // Invalidate the user's session
        if (httpSession != null) {
            // Invalidate the session if the user's email matches the one stored in the session
            if (email.equals(httpSession.getAttribute("userEmail"))) {
                httpSession.invalidate();
                // Clear the security context
                SecurityContextHolder.clearContext();
            }
        }
    }
}




