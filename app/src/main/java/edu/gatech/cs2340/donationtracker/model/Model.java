package edu.gatech.cs2340.donationtracker.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final Model instance = new Model();
    public static Model getInstance() { return instance;}

    private static ArrayList<Location> locationList;

    private static List<Item> itemList;

    private static User _user;

    public Model() {
        locationList = new ArrayList<Location>(6);
        itemList = new ArrayList<>();
    }

    public static void addLocation(Location location) {
        locationList.add(location);
    }

    public static void addItems(List<Item> items) {
        for (Item item : items) {
            addItem(item);
        }
    }

    public static void addItem(Item item) { itemList.add(item);}

    public static void setUser(User user) {
        _user = user;
    }

    public static Location get(int index) {
        return locationList.get(index);
    }

    public static Item getItem(int index) { return itemList.get(index);}

    public static String[] getLocationListArray () {
        String[] locationArray = new String[locationList.size()];
        for (int i = 0; i < locationList.size(); i++) {
            locationArray[i] = locationList.get(i).getLocationName();
        }
        return locationArray;
    }

    public static List<String> getLocationStringList() {
        ArrayList<String> arr = new ArrayList<>(locationList.size());
        for (Location loc: locationList) {
            arr.add(loc.getLocationName());
        }
        return arr;
    }

    public static String[] getItemListArray () {
        String[] items = new String[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            items[i] = itemList.get(i).getShortDescription();
        }
        return items;
    }

    public static ArrayList<Location> getLocationList() { return locationList;}

}
