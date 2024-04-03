package com.joaoneto.parkinglot.web.exceptions.exceptionBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

public record ExceptionResponseBody(
        Instant timestamp,
        Integer status,
        String error,
        String path,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Object fieldErrors
) {
    public ExceptionResponseBody(
            Instant timestamp,
            Integer status,
            String error,
            String path) {
        this(timestamp, status, error, path, null);
    }

    public ExceptionResponseBody(
            HttpServletRequest request,
            Integer status,
            String error) {
        this(Instant.now(), status, error, request.getRequestURI());

    }
}

