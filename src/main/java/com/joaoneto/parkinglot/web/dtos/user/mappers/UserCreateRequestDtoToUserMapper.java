package com.joaoneto.parkinglot.web.dtos.user.mappers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.web.dtos.user.UserCreateRequestDto;

import java.util.function.Function;

public class UserCreateRequestDtoToUserMapper implements Function <UserCreateRequestDto, User> {
    public static UserCreateRequestDtoToUserMapper build() {
        return new UserCreateRequestDtoToUserMapper();
    }

    @Override
    public User apply(UserCreateRequestDto user) {
        return new User(user.username(), user.password());
    }
}
