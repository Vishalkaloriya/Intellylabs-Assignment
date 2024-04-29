package com.example.task.Service.impl;

import com.example.task.Dto.LoginDTO;
import com.example.task.Dto.UserDTO;
import com.example.task.Entity.User;
import com.example.task.Repo.UserRepo;
import com.example.task.responce.LoginResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserIMPL.class})
@DisabledInAotMode
@ExtendWith(MockitoExtension.class)
class UserIMPLTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private UserIMPL userIMPL;

    @Test
    void testAddUser() {
        // Arrange
        UserDTO userDTO = new UserDTO(1, "janedoe", "jane.doe@example.org", "iloveyou");
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("secret");
        when(userRepo.save(any(User.class))).thenReturn(new User(1, "janedoe", "jane.doe@example.org", "secret"));

        // Act
        String actualAddUserResult = userIMPL.addUser(userDTO);

        // Assert
        verify(userRepo, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(any(CharSequence.class));
        assertEquals("janedoe", actualAddUserResult);
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("jane.doe@example.org", "iloveyou");
        User user = new User(1, "janedoe", "jane.doe@example.org",
                "$2a$10$zFl/9iPd4GZxuQxOJyQ1l.i/oI3r9oZ7ZdjmTq2v4MC.VYVbFzVDW"); // Password: iloveyou
        when(userRepo.findByEmail(loginDTO.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(true);

        // Act
        LoginResponse loginResponse = userIMPL.loginUser(loginDTO);

        // Assert
        verify(httpSession, times(1)).setAttribute(eq("userEmail"), eq("jane.doe@example.org"));
        assertEquals("Login Success", loginResponse.getMessage());
        assertEquals(true, loginResponse.getStatus());
    }

    @Test
    void testLoginUser_PasswordNotMatch() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("jane.doe@example.org", "iloveyou");
        User user = new User(1, "janedoe", "jane.doe@example.org",
                "$2a$10$zFl/9iPd4GZxuQxOJyQ1l.i/oI3r9oZ7ZdjmTq2v4MC.VYVbFzVDW"); // Password: iloveyou
        when(userRepo.findByEmail(loginDTO.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(false);

        // Act
        LoginResponse loginResponse = userIMPL.loginUser(loginDTO);

        // Assert
        assertEquals("Password Not Match", loginResponse.getMessage());
        assertEquals(false, loginResponse.getStatus());
    }

    @Test
    void testLoginUser_EmailNotExist() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("jane.doe@example.org", "iloveyou");
        when(userRepo.findByEmail(loginDTO.getEmail())).thenReturn(null);

        // Act
        LoginResponse loginResponse = userIMPL.loginUser(loginDTO);

        // Assert
        assertEquals("Email not exists", loginResponse.getMessage());
        assertEquals(false, loginResponse.getStatus());
    }

    @Test
    void testIsUserExists_True() {
        // Arrange
        String email = "jane.doe@example.org";
        when(userRepo.findByEmail(email)).thenReturn(new User());

        // Act
        boolean result = userIMPL.isUserExists(email);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void testIsUserExists_False() {
        // Arrange
        String email = "jane.doe@example.org";
        when(userRepo.findByEmail(email)).thenReturn(null);

        // Act
        boolean result = userIMPL.isUserExists(email);

        // Assert
        assertEquals(false, result);
    }

    @Test
    void testLogoutUser2() {
        // Arrange
        when(httpSession.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

        // Act
        userIMPL.logoutUser("jane.doe@example.org");

        // Assert that nothing has changed
        verify(httpSession).getAttribute(eq("userEmail"));
    }

    /**
     * Method under test: {@link UserIMPL#logoutUser(String)}
     */
    @Test
    void testLogoutUser3() {
        // Arrange
        doNothing().when(httpSession).invalidate();
        when(httpSession.getAttribute(Mockito.<String>any())).thenReturn("jane.doe@example.org");

        // Act
        userIMPL.logoutUser("jane.doe@example.org");

        // Assert
        verify(httpSession).getAttribute(eq("userEmail"));
        verify(httpSession).invalidate();
    }
}
