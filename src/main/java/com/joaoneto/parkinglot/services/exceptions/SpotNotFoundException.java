package com.joaoneto.parkinglot.services.exceptions;

public class SpotNotFoundException extends RuntimeException{
    public SpotNotFoundException(String message){
        super(message);
    }
}
