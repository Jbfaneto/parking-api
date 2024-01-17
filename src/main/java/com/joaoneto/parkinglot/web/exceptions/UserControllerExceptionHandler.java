package com.joaoneto.parkinglot.web.exceptions;

import com.joaoneto.parkinglot.services.exceptions.IllegalPasswordException;
import com.joaoneto.parkinglot.services.exceptions.UserNotFoundException;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



import java.time.Instant;

@ControllerAdvice("com.joaoneto.parkinglot.web.controllers")
public class UserControllerExceptionHandler {
    @ExceptionHandler(value = { UserNotFoundException.class })
    protected ResponseEntity<ExceptionResponseBody> handleUserNotFoundException(
            final UserNotFoundException exception,
            final HttpServletRequest request) {
        final var body = new ExceptionResponseBody(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(value = { IllegalPasswordException.class })
    protected ResponseEntity<ExceptionResponseBody> handleIllegalPasswordException(
            final IllegalPasswordException exception,
            final HttpServletRequest request) {
        final var body = new ExceptionResponseBody(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<ExceptionResponseBody> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception,
            final HttpServletRequest request,
            final BindingResult result) {

        final var body = new ExceptionResponseBody(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                request.getRequestURI(),
                result.getFieldErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<ExceptionResponseBody> handleIllegalArgumentException(
            final IllegalArgumentException exception,
            final HttpServletRequest request) {
        final var body = new ExceptionResponseBody(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
