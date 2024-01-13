package com.joaoneto.parkinglot.web.dtos.mappers;

import com.joaoneto.parkinglot.web.dtos.UserCreateRequestDto;
import com.joaoneto.parkinglot.web.dtos.UserCreateResponseDto;

import java.util.function.Function;

public class UserCreateRequestDtoToUserCreateResponseDtoMapper implements Function<UserCreateRequestDto, UserCreateResponseDto> {
    public static UserCreateRequestDtoToUserCreateResponseDtoMapper build() {
        return new UserCreateRequestDtoToUserCreateResponseDtoMapper();
    }

    @Override
    public UserCreateResponseDto apply(UserCreateRequestDto userCreateRequestDto) {
        String role = userCreateRequestDto.role().name().substring("ROLE_".length());
        return new UserCreateResponseDto(userCreateRequestDto.username(), role);
    }
}
