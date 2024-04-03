package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.entities.User;
import com.joaoneto.parkinglot.services.UserService;
import com.joaoneto.parkinglot.web.dtos.mappers.*;
import com.joaoneto.parkinglot.web.dtos.user.*;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Users", description = "User controller for creating, updating and getting users")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user", description = "Create a new user",
            tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCreateResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "400", description = "Invalid password or username",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class)))
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCreateResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto user) {
        User newUser = UserCreateRequestDtoToUserMapper.build().apply(user);
        userService.createUser(newUser);
        UserCreateResponseDto response = UserCreateRequestDtoToUserCreateResponseDtoMapper.build().apply(user);
        URI uri = URI.create(String.format("/api/v1/users/%s", newUser.getId()));
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Get user by id", description = "Get user by id",
            tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        GetUserDto response = UserToGetUserDtoMapper.build().apply(user);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update user password", description = "Update user password",
            tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "User password updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "400", description = "Password confirmation does not match",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class))),
            @ApiResponse(responseCode = "400", description = "Invalid password",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseBody.class)))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, CLIENT') AND #id = authentication.principal.id")
    public ResponseEntity<UpdateUserResponseDto> updatePassword(@PathVariable Long id, @Valid @RequestBody UpdateUserRequestDto user) {
        User updatedUser = userService.updatePassword(id, user.currentPassword(), user.newPassword(), user.passwordConfirmation());
        UpdateUserResponseDto response = UserToUpdateUserResponseDtoMapper.build().apply(updatedUser);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Get all users", description = "Get all users",
            tags = {"Users"}, responses = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListGetUserDto.class))),
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GetUserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<GetUserDto>response = ListGetUserDtoMapper.build().apply(users);
        return ResponseEntity.ok(response);

    }
}
