package com.joaoneto.parkinglot.entities.enums;

public enum UserRole {
    ROLE_ADMIN("Admin"),
    ROLE_CLIENT("Client");

    private final String stringValue;

    UserRole(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}

