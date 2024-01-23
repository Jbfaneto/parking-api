package com.joaoneto.parkinglot.repositories;

import com.joaoneto.parkinglot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
