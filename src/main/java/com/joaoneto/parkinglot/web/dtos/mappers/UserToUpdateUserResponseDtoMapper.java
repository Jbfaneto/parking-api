package com.joaoneto.parkinglot.web.dtos.mappers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.web.dtos.user.UpdateUserResponseDto;

import java.util.function.Function;

public class UserToUpdateUserResponseDtoMapper implements Function<User, UpdateUserResponseDto> {
    public static UserToUpdateUserResponseDtoMapper build() {
        return new UserToUpdateUserResponseDtoMapper();
    }

    @Override
    public UpdateUserResponseDto apply(User user) {
        if (user != null) {
            String role = user.getRole().name().substring("ROLE_".length());
            return new UpdateUserResponseDto(user.getId(), user.getUsername(), user.getPassword(), role);
        }
        return null;
    }
}
