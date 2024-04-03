package com.joaoneto.parkinglot.web.dtos.mappers;

import com.joaoneto.parkinglot.web.dtos.user.UserCreateRequestDto;
import com.joaoneto.parkinglot.web.dtos.user.UserCreateResponseDto;

import java.util.function.Function;

public class UserCreateRequestDtoToUserCreateResponseDtoMapper implements Function<UserCreateRequestDto, UserCreateResponseDto> {
    public static UserCreateRequestDtoToUserCreateResponseDtoMapper build() {
        return new UserCreateRequestDtoToUserCreateResponseDtoMapper();
    }

    @Override
    public UserCreateResponseDto apply(UserCreateRequestDto userCreateRequestDto) {
        String role = "client";
        return new UserCreateResponseDto(userCreateRequestDto.username(), role);
    }
}
