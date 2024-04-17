package com.joaoneto.parkinglot.services.exceptions;

public class ClientSpotNotFoundException extends RuntimeException{
    public ClientSpotNotFoundException(String message) {
        super(message);
    }
}
