package com.joaoneto.parkinglot.web.dtos.mappers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.web.dtos.UpdateUserRequestDto;

import java.util.function.Function;

public class UpdateUserRequestDtoToUserMapper implements Function<UpdateUserRequestDto, User> {
    public static UpdateUserRequestDtoToUserMapper build() {
        return new UpdateUserRequestDtoToUserMapper();
    }

    @Override
    public User apply(UpdateUserRequestDto user) {
        User userNewPassword = new User();
        userNewPassword.setPassword(user.password());
        return userNewPassword;
    }
}
