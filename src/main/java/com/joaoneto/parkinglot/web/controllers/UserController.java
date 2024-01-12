package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        URI uri = URI.create(String.format("/api/v1/users/%s", newUser.getId()));
        return ResponseEntity.created(uri).body(newUser);
    }

}
