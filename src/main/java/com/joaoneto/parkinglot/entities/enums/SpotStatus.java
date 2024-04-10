package com.joaoneto.parkinglot.entities.enums;

public enum SpotStatus {
    FREE("free"),
    OCCUPIED("occupied");

    private final String stringValue;

    SpotStatus(String stringValue) {
        this.stringValue = stringValue;
    }
}
