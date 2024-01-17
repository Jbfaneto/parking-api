package com.joaoneto.parkinglot.web.exceptions.exceptionBody;

import java.time.Instant;

public record ExceptionResponseBody(
        Instant timestamp,
        Integer status,
        String error,
        String path,
        Object fieldErrors
) {
    public ExceptionResponseBody(
            Instant timestamp,
            Integer status,
            String error,
            String path) {
        this(timestamp, status, error, path, null);
    }
}

