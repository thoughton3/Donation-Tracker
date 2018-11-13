package edu.gatech.cs2340.donationtracker.model;

import android.arch.persistence.room.TypeConverter;


/**
 * represents a Converter
 */
public class Converters {

    /**
     * takes an accountType and makes it a String
     * @param type the accountType
     * @return the accountType as a String
     */
    @TypeConverter
    public static String fromAccountTypeToString(AccountType type) {
        return type.getValue();
    }

    /**
     * takes a String and makes it an accountType
     * @param string the String being converted
     * @return the accountType
     */
    @TypeConverter
    public static AccountType fromStringToAccountType(String string) {
        if (string.equals(AccountType.USER.getValue())) {
            return AccountType.USER;
        } else if (string.equals(AccountType.LOCATION_EMPLOYEE.getValue())) {
            return AccountType.LOCATION_EMPLOYEE;
        } else if (string.equals(AccountType.ADMIN.getValue())) {
            return AccountType.ADMIN;
        } else {
            return null;
        }
    }

    /**
     * takes a Location and makes it a String
     * @param location the Location being converted
     * @return the String of the Location
     */
    @TypeConverter
    public static String fromLocationToString(Location location)
    { return location.getLocationName();}

    /**
     * takes a String and converts it to a Location
     * @param string the String being converted
     * @return the Location representation of the String
     */
    @TypeConverter
    public static Location fromStringToLocation(String string) {
        for (Location location : Model.getLocationList()) {
            if (location.getLocationName().equals(string)) {
                return location;
            }
        }
        return null;
    }

    /**
     * takes an itemType and makes it a String
     * @param type the itemType being converted
     * @return the String of the itemType
     */
    @TypeConverter
    public static String fromItemTypeToString(ItemType type) {
        return type.getValue();
    }

    /**
     * converts a String to an itemType
     * @param string the String being converted
     * @return the itemType of the String
     */
    @TypeConverter
    public static ItemType fromStringToItemType(String string) {
        if (string.equals(ItemType.CLOTHING.getValue())) {
            return ItemType.CLOTHING;
        } else if (string.equals(ItemType.KITCHEN.getValue())) {
            return ItemType.KITCHEN;
        } else if (string.equals(ItemType.HAT.getValue())) {
            return ItemType.HAT;
        } else if (string.equals(ItemType.ELECTRONICS.getValue())) {
            return ItemType.ELECTRONICS;
        } else if (string.equals(ItemType.HOUSEHOLD.getValue())) {
            return ItemType.HOUSEHOLD;
        } else if (string.equals(ItemType.OTHER.getValue())) {
            return ItemType.OTHER;
        } else {
            return null;
        }
    }






/*    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    @TypeConverter
    public String fromLocationToName(Location location) {
        locations.add(location);
        return location.getLocationName();
    }*/

/*    @TypeConverter
    public Location fromNameToLocation(String name) {
        if (locations.contains(new Location(name))) {
            return location;
        }
        return null;
    }

    public String fromItemToName(Item item) {
        this.item = item;
        return location
    }*/
}
