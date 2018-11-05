package edu.gatech.cs2340.donationtracker.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final Model instance = new Model();
    public static Model getInstance() { return instance;}

    private static ArrayList<Location> locationList;

    private static User _user;

    public Model() {
        locationList = new ArrayList<Location>(6);
    }

    public static void addLocation(Location location) {
        locationList.add(location);
    }

    public static void setUser(User user) {
        _user = user;
    }

    public static Location get(int index) {
        return locationList.get(index);
    }

}
