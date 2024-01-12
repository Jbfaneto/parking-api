package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.repositories.UserRepository;
import com.joaoneto.parkinglot.services.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    @Transactional
    public User updatePassword(Long id, String password) {
        User userToUpdate = getUserById(id);
        userToUpdate.setPassword(password);
        return userToUpdate;
    }
}
