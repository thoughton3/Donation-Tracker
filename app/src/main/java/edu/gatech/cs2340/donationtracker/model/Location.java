package edu.gatech.cs2340.donationtracker.model;

public class Location {
    private String locationName;
    private String locationType;
    private String longitude;
    private String latitude;
    private String address;
    private String phoneNumber;

    public Location(String locationName, String locationType, String longitude, String latitude, String address, String phoneNumber) {
        this.locationName = locationName;
        this.locationType = locationType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return "Location Name: " + locationName + "\nLocation Type: " + locationType + "\nLongitude: " +
                longitude + "\nLatitude: " + latitude + "\nAddress: " + address + "\nPhone Number: " + phoneNumber;
    }
}
