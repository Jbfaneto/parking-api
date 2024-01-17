package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.repositories.UserRepository;
import com.joaoneto.parkinglot.services.exceptions.IllegalPasswordException;
import com.joaoneto.parkinglot.services.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String passwordConfirmation) {
        User userToUpdate = getUserById(id);
        if (!userToUpdate.getPassword().equals(currentPassword)) {
            throw new IllegalArgumentException("Current password does not match");
        }

        validatePassword(currentPassword, newPassword, passwordConfirmation);

        userToUpdate.setPassword(newPassword);
        return userToUpdate;
    }

    private void validatePassword(String currentPassword, String newPassword, String passwordConfirmation) {

        if (!newPassword.equals(passwordConfirmation)) {
            throw new IllegalPasswordException("New password does not match password confirmation");
        }

        if (newPassword.equals(currentPassword)) {
            throw new IllegalArgumentException("New password must be different from current password");
        }

    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }
}
