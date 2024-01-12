package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(updatedUser);
    }
}
