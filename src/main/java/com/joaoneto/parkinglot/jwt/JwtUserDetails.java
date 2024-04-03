package com.joaoneto.parkinglot.jwt;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


public class JwtUserDetails extends User {
    private final com.joaoneto.parkinglot.entities.User user;

    public JwtUserDetails(com.joaoneto.parkinglot.entities.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this .user = user;
    }

    public long getId() {
        return user.getId();
    }

    public String getRole() {
        return user.getRole().name();
    }
}
