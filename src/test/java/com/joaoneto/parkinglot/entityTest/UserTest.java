package com.joaoneto.parkinglot.entityTest;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.entities.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    @Test
    void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testUser");
        user1.setPassword("testPassword");
        user1.setRole(UserRole.ROLE_ADMIN);
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());
        user1.setCreatedBy("testCreator");
        user1.setUpdatedBy("testUpdater");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("testUser");
        user2.setPassword("testPassword");
        user2.setRole(UserRole.ROLE_ADMIN);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());
        user2.setCreatedBy("testCreator");
        user2.setUpdatedBy("testUpdater");

        assertTrue(user1.equals(user2) && user2.equals(user1));
        assertTrue(user1.hashCode() == user2.hashCode());
    }

    @Test
    void testToString() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole(UserRole.ROLE_ADMIN);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedBy("testCreator");
        user.setUpdatedBy("testUpdater");

        String expectedString = "User{id=1}";
        assertEquals(expectedString, user.toString());
    }
}
