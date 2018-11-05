package edu.gatech.cs2340.donationtracker.model;

public enum ItemType {
    CLOTHING("CLOTHING"), HAT("HAT"), KITCHEN("KITCHEN"), ELECTRONICS("ELECTRONICS"), HOUSEHOLD("HOUSEHOLD"), OTHER("OTHER");

    private String valueOf;
    private ItemType(String string) {
        this.valueOf = string;
    }

    public String getValue() { return valueOf;}
}
