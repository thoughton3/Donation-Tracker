package edu.gatech.cs2340.donationtracker.model;


import java.util.ArrayList;
import java.util.List;

/**
 * a representation of the charity
 */
public class Model {
    private static final Model instance = new Model();

    /**
     * getter for the instance of the Model
     * @return the instance of the Model
     */
    public static Model getInstance() { return instance;}

    private static ArrayList<Location> locationList;

    private static List<Item> itemList;

    /**
     * constructor for Model
     */
    public Model() {
        locationList = new ArrayList<>(6);
        itemList = new ArrayList<>();
    }

    /**
     * adds a Location to the location list
     * @param location the Location being added
     */
    public static void addLocation(Location location) {
        locationList.add(location);
    }

    /**
     * adds a list if items
     * @param items the list of items being added
     */
    public static void addItems(Iterable<Item> items) {
        for (Item item : items) {
            addItem(item);
        }
    }

    /**
     * adds an item
     * @param item the item being added
     */
    public static void addItem(Item item) { itemList.add(item);}


    /**
     * getter for a Location
     * @param index the index of the Location in the list
     * @return the Location
     */
    public static Location get(int index) {
        return locationList.get(index);
    }

    /**
     * getter for an Item
     * @param index the index the Item is located at
     * @return the Item
     */
    public static Item getItem(int index) { return itemList.get(index);}


    /**
     * getter for item list
     * @return list of items
     */
    public static String[] getItemListArray () {
        String[] items = new String[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            items[i] = itemList.get(i).getShortDescription();
        }
        return items;
    }

    /**
     * getter for list of Locations
     * @return list of Locations
     */
    public static ArrayList<Location> getLocationList() { return locationList;}

}
