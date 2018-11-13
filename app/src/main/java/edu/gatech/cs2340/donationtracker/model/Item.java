package edu.gatech.cs2340.donationtracker.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * this represents an item
 */
@Entity(tableName = "items")
public class Item {
    @PrimaryKey
    @ColumnInfo(name = "short_description")
    @NonNull
    private final String shortDescription;
    private final String fullDescription;
    private final double value;
    private ItemType category;
    private String categoryString;
    private final String comments;
    private final Location location;

    /**
     * constructs an Item
     * @param shortDescription short description of the Item
     * @param fullDescription full description of the Item
     * @param value value of the Item
     * @param category category the item belongs in
     * @param comments comments about the Item
     * @param location location of the Item
     */
    public Item(@NonNull String shortDescription, String fullDescription,
                double value, ItemType category,
                String comments, Location location) {
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.category = category;
        this.comments = comments;
        this.location = location;
    }

    /**
     * construct an item
     * @param shortDescription short description of the Item
     * @param fullDescription full description of the Item
     * @param value value of the Item
     * @param categoryString category of the Item as a String
     * @param comments comments about the Item
     * @param location location of the Item
     */
    @Ignore
    public Item(@NonNull String shortDescription, String fullDescription, double value,
                String categoryString, String comments, Location location) {
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.categoryString = categoryString;
        this.comments = comments;
        this.location = location;
    }


    @Override
    public String toString() {
        return "Short Description: " + shortDescription + "\nFull Description: " +
                fullDescription + "\nValue: $" + value + "\nCategory: " + category +
                "\nComments: " + comments;
    }

    /**
     * getter for short description
     * @return the shortDescription of the Item
     */
    @NonNull
    public String getShortDescription() {
        return shortDescription;
    }


    /**
     * getter for fullDescription of the Item
     * @return the fullDescription of the Item
     */
    public String getFullDescription() {return fullDescription;}


    /**
     * getter for the value of the Item
     * @return the value of the Item
     */
    public double getValue() { return value;}


    /**
     * getter for the category of the Item
     * @return the category of the Item
     */
    public ItemType getCategory() {
        return category;
    }



    /**
     * getter for the comments about the Item
     * @return the comments about the Item
     */
    public String getComments() {
        return comments;
    }



    /**
     * getter for the category as a String
     * @return the category of the Item as a String
     */
    public String getCategoryString() {
        return categoryString;
    }

    /**
     * setter for the category as a String
     * @param categoryString the category of Item as a String
     */
    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }


    @Override
    public boolean equals(Object obj) {
        String description = this.getShortDescription();
        return (obj instanceof Item) &&
                (description.equals(((Item) obj).getShortDescription()));
    }

}
