package com.joaoneto.parkinglot.service;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.entities.enums.UserRole;
import com.joaoneto.parkinglot.repositories.UserRepository;
import com.joaoneto.parkinglot.services.UserService;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;


    private User user;

    @Test
    public void shouldCreateUser(){
        //arrange
        user = new User(1l, "João", "1234567", UserRole.ROLE_ADMIN, null, null, null, null);
        // act
        userRepository.save(user);

        //assert
        then(userRepository).should().save(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();
        Assertions.assertNotNull(capturedUser);
        Assertions.assertEquals(capturedUser, user);

    }
}
