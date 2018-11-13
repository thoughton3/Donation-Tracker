package edu.gatech.cs2340.donationtracker.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "items")
public class Item {
    @PrimaryKey
    @ColumnInfo(name = "short_description")
    @NonNull
    private String shortDescription;
    private String fullDescription;
    private double value;
    private ItemType category;
    private String categoryString;
    private String comments;
    private Location location;

    public Item(String shortDescription, String fullDescription, double value, ItemType category, String comments, Location location) {
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.category = category;
        this.comments = comments;
        this.location = location;
    }

    @Ignore
    public Item(String shortDescription, String fullDescription, double value, String categoryString, String comments, Location location) {
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.categoryString = categoryString;
        this.comments = comments;
        this.location = location;
    }


    @Override
    public String toString() {
        return "Short Description: " + shortDescription + "\nFull Description: " + fullDescription + "\nValue: $" + value + "\nCategory: " + category + "\nComments: " + comments;
    }

    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String description) { shortDescription = description;}
    public String getFullDescription() {return fullDescription;}
    public void setFullDescription(String description) { fullDescription = description;}
    public double getValue() { return value;}
    public void setValue(double val) { value = val;}

    public ItemType getCategory() {
        return category;
    }

    public void setCategory(ItemType category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getShortDescription().equals(((Item) obj).getShortDescription());
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
