package edu.gatech.cs2340.donationtracker;

import java.util.ArrayList;

public enum AccountType {
    USER("User"), LOCATION_EMPLOYEE("Location Employee"), ADMIN("Admin");

    private String value;
    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
