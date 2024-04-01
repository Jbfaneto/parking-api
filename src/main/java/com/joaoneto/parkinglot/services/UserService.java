package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.repositories.UserRepository;
import com.joaoneto.parkinglot.services.exceptions.IllegalPasswordException;
import com.joaoneto.parkinglot.services.exceptions.UserNotFoundException;
import com.joaoneto.parkinglot.services.exceptions.UsernameUniqueViolationException;
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
        validateUsername(user);
        return userRepository.save(user);
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validateUsername(User user) {
        if (findByUsername(user.getUsername()) != null) {
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

        userToUpdate.setPassword(newPassword);

        return userToUpdate;
    }



    private void validatePassword(User user, String currentPassword, String newPassword, String passwordConfirmation) {

        if (!newPassword.equals(passwordConfirmation)) {
            throw new IllegalPasswordException("New password does not match password confirmation");
        }

        if (newPassword.equals(currentPassword) && !newPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from current password");
        }

    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

}
