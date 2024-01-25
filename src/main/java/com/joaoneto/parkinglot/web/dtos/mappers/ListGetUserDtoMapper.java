package com.joaoneto.parkinglot.web.dtos.mappers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.web.dtos.GetUserDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListGetUserDtoMapper implements Function<List<User>, List<GetUserDto>> {
    public static ListGetUserDtoMapper build() {
        return new ListGetUserDtoMapper();
    }

    @Override
    public List<GetUserDto> apply(List<User> users) {
        return users.stream()
                .map(user -> UserToGetUserDtoMapper.build().apply(user))
                .collect(Collectors.toList());
    }
}
