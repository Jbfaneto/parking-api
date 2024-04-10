package com.joaoneto.parkinglot.services.exceptions;

public class SpotAlreadyExistsException extends RuntimeException{
    public SpotAlreadyExistsException(String message) {
        super(message);
    }
}
