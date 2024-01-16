package com.joaoneto.parkinglot.web.dtos.mappers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.web.dtos.GetUserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListGetUserDto implements Function<List<User>, List<GetUserDto>> {
    public static ListGetUserDto build() {
        return new ListGetUserDto();
    }

    @Override
    public List<GetUserDto> apply(List<User> users) {
        List<GetUserDto> response = new ArrayList<>();

        for (User user : users) {
            GetUserDto userResponse = UserToGetUserDtoMapper.build().apply(user);
            response.add(userResponse);
        }

        return response;
    }
}
