package com.joaoneto.parkinglot.web.dtos.user.mappers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.web.dtos.user.GetUserDto;

import java.util.function.Function;

public class UserToGetUserDtoMapper implements Function<User, GetUserDto> {
    public static UserToGetUserDtoMapper build() {
        return new UserToGetUserDtoMapper();
    }

    @Override
    public GetUserDto apply(User user) {
        if (user != null) {
            String role = user.getRole().name().substring("ROLE_".length());
            return new GetUserDto(user.getId(), user.getUsername(), role);
        }
        return null;
    }
}
