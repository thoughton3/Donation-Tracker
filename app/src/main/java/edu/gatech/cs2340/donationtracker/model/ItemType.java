package edu.gatech.cs2340.donationtracker.model;

/**
 * enums that represent the possible types of Items
 */
public enum ItemType {
    CLOTHING("CLOTHING"), HAT("HAT"), KITCHEN("KITCHEN"), ELECTRONICS("ELECTRONICS"),
    HOUSEHOLD("HOUSEHOLD"), OTHER("OTHER");

    private final String valueOf;
    ItemType(String string) {
        this.valueOf = string;
    }

    /**
     * gets the value of the enum
     * @return the value
     */
    public String getValue() { return valueOf;}
}
