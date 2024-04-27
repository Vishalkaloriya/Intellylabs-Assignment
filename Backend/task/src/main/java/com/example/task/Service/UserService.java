package com.example.task.Service;

import com.example.task.Dto.UserDTO;
import com.example.task.Dto.LoginDTO;
import com.example.task.responce.LoginResponse;

public interface UserService {
    String addUser(UserDTO userDTO);

    LoginResponse loginUser(LoginDTO loginDTO);

    boolean isUserExists(String email);

    void logoutUser(String email);

}


