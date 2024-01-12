package com.joaoneto.parkinglot.controller;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.entities.enums.UserRole;
import com.joaoneto.parkinglot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final MockMvc mockMvc;

    public UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole(UserRole.ROLE_ADMIN);
    }

    @Test
    public void testCreateUser() throws Exception {
        User savedUser = new User();
        savedUser.setUsername(user.getUsername());
        savedUser.setPassword(user.getPassword());
        savedUser.setRole(user.getRole());

        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"password\":\"testPassword\", \"role\":\"ROLE_ADMIN\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(savedUser.getUsername())))
                .andExpect(jsonPath("$.role", is(savedUser.getRole().toString())));
    }
}

