package edu.gatech.cs2340.donationtracker.model;


/**
 * represents the drop off locations
 */
@SuppressWarnings("TypeMayBeWeakened")
public class Location {
    private final String locationName;
    private final String locationType;
    private final String longitude;
    private final String latitude;
    private final String address;
    private final String phoneNumber;


    /**
     * constructor of a Location
     * @param locationName the name of the Location
     * @param locationType the type of the Location
     * @param longitude the longitude of the location
     * @param latitude the latitude of the location
     * @param address the address of the location
     * @param phoneNumber the phone number for the location
     */
    public Location(String locationName, String locationType,
                    String longitude, String latitude, String address, String phoneNumber) {
        this.locationName = locationName;
        this.locationType = locationType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * getter for the information associated with a location
     * @return a String containing the information of a location
     */
    public CharSequence getLocationInfo() {
        return "Location Name: " + locationName + "\nLocation Type: " +
                locationType + "\nLongitude: " +
                longitude + "\nLatitude: " + latitude + "\nAddress: " + address +
                "\nPhone Number: " + phoneNumber;
    }

    /**
     * getter for the name of the Location
     * @return the name of the Location
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * getter for the latitude of a location
     * @return the latitude of the location
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * getter for the longitude of a location
     * @return the longitude of the location
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * getter for the phone number of a location
     * @return the phone number of the Location
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return locationName;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getLocationName().equals(((Location) obj).getLocationName());
    }
}
