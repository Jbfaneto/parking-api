package com.joaoneto.parkinglot.repositories;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u.role FROM User u WHERE u.username = :username")
    UserRole findRoleByUsername(String username);
}
