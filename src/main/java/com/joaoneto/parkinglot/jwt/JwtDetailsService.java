package com.joaoneto.parkinglot.jwt;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.entities.enums.UserRole;
import com.joaoneto.parkinglot.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String username) {
        UserRole role = userService.findRoleByUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
