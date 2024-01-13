package com.joaoneto.parkinglot.web.dtos.mappers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.web.dtos.UserCreateDto;

import java.util.function.Function;

public class UserCreateDtoToUserMapper implements Function <UserCreateDto, User> {
    public static UserCreateDtoToUserMapper build() {
        return new UserCreateDtoToUserMapper();
    }


    @Override
    public User apply(UserCreateDto user) {
        return new User(user.username(), user.password(), user.role());
    }
}
