package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.services.UserService;
import com.joaoneto.parkinglot.web.dtos.*;
import com.joaoneto.parkinglot.web.dtos.mappers.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCreateResponseDto> createUser(@RequestBody UserCreateRequestDto user) {
        User newUser = UserCreateRequestDtoToUserMapper.build().apply(user);
        userService.createUser(newUser);
        UserCreateResponseDto response = UserCreateRequestDtoToUserCreateResponseDtoMapper.build().apply(user);
        URI uri = URI.create(String.format("/api/v1/users/%s", newUser.getId()));
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        GetUserDto response = UserToGetUserDtoMapper.build().apply(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponseDto> updatePassword(@PathVariable Long id, @RequestBody UpdateUserRequestDto user) {
        User updatedUser = userService.updatePassword(id, user.currentPassword(), user.newPassword(), user.passwordConfirmation());
        UpdateUserResponseDto response = UserToUpdateUserResponseDtoMapper.build().apply(updatedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetUserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<GetUserDto> response = ListGetUserDto.build().apply(users);
        return ResponseEntity.ok(response);

    }
}
