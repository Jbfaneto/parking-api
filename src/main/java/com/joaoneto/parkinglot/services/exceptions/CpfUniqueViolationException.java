package com.joaoneto.parkinglot.services.exceptions;

public class CpfUniqueViolationException extends RuntimeException{
    public CpfUniqueViolationException(String message) {
        super(message);
    }
}
