package com.joaoneto.parkinglot.web.dtos.mappers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.web.dtos.GetUserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListGetUserDto implements Function<List<User>, List<GetUserDto>> {
    public static ListGetUserDto build() {
        return new ListGetUserDto();
    }

    @Override
    public List<GetUserDto> apply(List<User> users) {
        List<GetUserDto> response = users.stream()
                    .map(user -> UserToGetUserDtoMapper.build().apply(user))
                    .collect(Collectors
                            .toList());
        return response;
    }
}
