package com.joaoneto.parkinglot.entities.enums;

public enum SpotStatus {
    FREE("free"),
    OCCUPIED("occupied");

    private final String stringValue;

    SpotStatus(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public static SpotStatus fromStringValue(String stringValue) {
        for (SpotStatus status : SpotStatus.values()) {
            if (status.getStringValue().equals(stringValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant for string value " + stringValue);
    }
}
