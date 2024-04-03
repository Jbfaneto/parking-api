package com.joaoneto.parkinglot.web.controllers;

import com.joaoneto.parkinglot.jwt.JwtDetailsService;
import com.joaoneto.parkinglot.jwt.JwtToken;
import com.joaoneto.parkinglot.web.dtos.authentication.UserLoginDto;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final JwtDetailsService jwtDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletRequest request) {
        System.out.println(userLoginDto.username());
        log.info("Authentication request received for user {}", userLoginDto.username());
        try {
           var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());
              authenticationManager.authenticate(usernamePasswordAuthenticationToken);
              JwtToken token = jwtDetailsService.getTokenAuthenticated(userLoginDto.username());
                return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.warn("Authentication failed for user {}", userLoginDto.username());
        }
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponseBody(request, HttpStatus.BAD_REQUEST.value(), "Invalid credentials"));
    }

}

