package com.example.task.UserController;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.task.Dto.LoginDTO;
import com.example.task.Dto.UserDTO;
import com.example.task.Service.UserService;
import com.example.task.responce.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserControllerDiffblueTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#loginUser(LoginDTO)}
     */
    @Test
    void testLoginUser() throws Exception {
        // Arrange
        when(userService.loginUser(Mockito.<LoginDTO>any())).thenReturn(new LoginResponse());

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("jane.doe@example.org");
        loginDTO.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":null,\"status\":null}"));
    }

    /**
     * Method under test: {@link UserController#loginUser(LoginDTO)}
     */
    @Test
    void testLoginUser2() throws Exception {
        // Arrange
        when(userService.loginUser(Mockito.<LoginDTO>any())).thenReturn(new LoginResponse());

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("");
        loginDTO.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Email and password are required"));
    }

    /**
     * Method under test: {@link UserController#loginUser(LoginDTO)}
     */
    @Test
    void testLoginUser3() throws Exception {
        // Arrange
        when(userService.loginUser(Mockito.<LoginDTO>any())).thenReturn(new LoginResponse());

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("jane.doe@example.org");
        loginDTO.setPassword("");
        String content = (new ObjectMapper()).writeValueAsString(loginDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Email and password are required"));
    }

    /**
     * Method under test: {@link UserController#logoutUser(String)}
     */
    @Test
    void testLogoutUser() throws Exception {
        // Arrange
        doNothing().when(userService).logoutUser(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/logout")
                .param("email", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Logged out successfully"));
    }

    /**
     * Method under test: {@link UserController#saveUser(UserDTO)}
     */
    @Test
    void testSaveUser() throws Exception {
        // Arrange
        when(userService.isUserExists(Mockito.<String>any())).thenReturn(true);
        when(userService.addUser(Mockito.<UserDTO>any())).thenReturn("Add User");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setPassword("iloveyou");
        userDTO.setUserId(1);
        userDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User already registered"));
    }

    /**
     * Method under test: {@link UserController#saveUser(UserDTO)}
     */
    @Test
    void testSaveUser2() throws Exception {
        // Arrange
        when(userService.isUserExists(Mockito.<String>any())).thenReturn(false);
        when(userService.addUser(Mockito.<UserDTO>any())).thenReturn("Add User");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setPassword("iloveyou");
        userDTO.setUserId(1);
        userDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User Registration Successful"));
    }
}
