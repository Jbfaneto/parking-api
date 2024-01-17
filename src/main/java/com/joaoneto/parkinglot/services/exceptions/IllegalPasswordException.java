package com.joaoneto.parkinglot.services.exceptions;

public class IllegalPasswordException extends RuntimeException{
    public IllegalPasswordException(String message) {
        super(message);
    }
}
