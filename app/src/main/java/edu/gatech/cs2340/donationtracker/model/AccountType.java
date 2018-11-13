package edu.gatech.cs2340.donationtracker.model;


/**
 * this class represents the enums of the different types of Users
 */
public enum AccountType {
    USER("User"), LOCATION_EMPLOYEE("Location Employee"), ADMIN("Admin");

    private final String value;
    AccountType(String value) {
        this.value = value;
    }

    /**
     * getter for value
     * @return value of enum
     */
    public String getValue() {
        return value;
    }

}
