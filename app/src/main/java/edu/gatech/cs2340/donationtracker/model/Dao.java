package edu.gatech.cs2340.donationtracker.model;

import android.arch.persistence.room.Query;

import java.util.List;
import android.arch.persistence.room.Insert;


/**
 * represents a Dao
 */
@android.arch.persistence.room.Dao
public interface Dao {
    /**
     * gets a list of the Users
     * @return a list of the Users
     */
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    /**
     * getter for list of Items
     * @return list of all Items
     */
    @Query("SELECT * FROM items")
    List<Item> getAllItems();

    /**
     * getter for items being searched for
     * @param search what they are searching for
     * @return a list of items that are being searched for
     */
    @Query("SELECT * FROM items WHERE short_description LIKE ('%' || :search || '%')")
    List<Item> getAllItemsByNameSearch(String search);

    /**
     * getter for all Items at a Location
     * @param string the Location
     * @return list of all the Items at that Location
     */
    @Query("SELECT * FROM items WHERE location LIKE :string")
    List<Item> getAllItemsFromLocation(String string);

    /**
     * gets a list of Items at a location by searching for them
     * @param location the Location
     * @param search what is being searched for
     * @return list of Items at the Location being searched for
     */
    @Query("SELECT * FROM items WHERE location LIKE :location AND short_description LIKE " +
            "('%' || :search || '%')")
    List<Item> getAllItemsAtLocationByNameSearch(String location, String search);

    /**
     * gets a list of Items at a Location by category
     * @param location the Location of the Items
     * @param category the category of the Items
     * @return list of Items at this Location in this category
     */
    @Query("SELECT * FROM items WHERE location LIKE :location AND category LIKE :category")
    List<Item> getAllItemsAtLocationByCategory(String location, String category);

    /**
     * gets a list of Items that are in a certain category
     * @param category the category the Items are in
     * @return list of Items in the category
     */
    @Query("SELECT * FROM items WHERE category LIKE :category")
    List<Item> getAllItemsByCategory(String category);

    /**
     * inserts a User
     * @param user the User
     */
    @Insert
    void insertUser(User user);

    /**
     * inserts an Item
     * @param item the Item
     */
    @Insert
    void insertItem(Item item);

}
