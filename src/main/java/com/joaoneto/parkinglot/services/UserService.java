package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.entities.enums.UserRole;
import com.joaoneto.parkinglot.repositories.UserRepository;
import com.joaoneto.parkinglot.services.exceptions.IllegalPasswordException;
import com.joaoneto.parkinglot.services.exceptions.UserNotFoundException;
import com.joaoneto.parkinglot.services.exceptions.UsernameUniqueViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        validateUsername(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", username)));
    }

    private void validateUsername(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameUniqueViolationException("Username already exists");
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String passwordConfirmation) {
        User userToUpdate = getUserById(id);

        validatePassword(userToUpdate, currentPassword, newPassword, passwordConfirmation);

        userToUpdate.setPassword(passwordEncoder.encode(newPassword));

        return userToUpdate;
    }



    private void validatePassword(User user, String currentPassword, String newPassword, String passwordConfirmation) {

        if (!newPassword.equals(passwordConfirmation)) {
            throw new IllegalPasswordException("New password does not match password confirmation");
        }

        if (newPassword.equals(currentPassword)) {
            throw new IllegalArgumentException("New password must be different from current password");
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalPasswordException("Current password does not match");
        }

    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    @Transactional
    public UserRole findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}
