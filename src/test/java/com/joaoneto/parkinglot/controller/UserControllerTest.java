package com.joaoneto.parkinglot.controller;

import com.joaoneto.parkinglot.ParkingLotApplication;
import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.entities.enums.UserRole;
import com.joaoneto.parkinglot.services.UserService;
import com.joaoneto.parkinglot.web.dtos.UserCreateRequestDto;
import com.joaoneto.parkinglot.web.dtos.UserCreateResponseDto;
import com.joaoneto.parkinglot.web.dtos.mappers.UserCreateRequestDtoToUserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootTest(classes = ParkingLotApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreateRequestDto userCreateRequestDto;

    @BeforeEach
    public void setUp() {
        userCreateRequestDto = new UserCreateRequestDto("testUser", "testPassword");
    }

    @Test
    public void testCreateUser() throws Exception {
        var userFromMapper = UserCreateRequestDtoToUserMapper.build().apply(userCreateRequestDto);

        var savedUser = createResponseDtoFromUser(userFromMapper);

        when(userService.createUser(any(User.class))).thenReturn(userFromMapper);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"password\":\"testPassword\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(savedUser.username())))
                .andExpect(jsonPath("$.role", is(savedUser.role())));
    }

    private UserCreateResponseDto createResponseDtoFromUser(User user) {
        String role = "client";
        return new UserCreateResponseDto(user.getUsername(), role);
    }
}

