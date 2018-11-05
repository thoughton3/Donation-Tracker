package edu.gatech.cs2340.donationtracker.model;

import java.util.List;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;


@android.arch.persistence.room.Dao
public interface Dao {
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM items")
    List<Item> getAllItems();

    @Query("SELECT * FROM items WHERE short_description LIKE ('%' || :search || '%')")
    List<Item> getAllItemsByNameSearch(String search);

    @Query("SELECT * FROM items WHERE location LIKE :string")
    List<Item> getAllItemsFromLocation(String string);

    @Query("SELECT * FROM items WHERE location LIKE :location AND short_description LIKE ('%' || :search || '%')")
    List<Item> getAllItemsAtLocationByNameSearch(String location, String search);

    @Query("SELECT * FROM items WHERE location LIKE :location AND category LIKE :category")
    List<Item> getAllItemsAtLocationByCategory(String location, String category);

    @Query("SELECT * FROM items WHERE category LIKE :category")
    List<Item> getAllItemsByCategory(String category);

    @Insert
    void insertUser(User user);

    @Insert
    void insertItem(Item item);

}
