package edu.gatech.cs2340.donationtracker.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * this class represents a User
 */
@Entity(tableName = "users")
public class User {
    @ColumnInfo(name = "name")
    private String name;
    @PrimaryKey

    @ColumnInfo(name = "username")
    @NonNull private String username = "";
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "account_type")
    private AccountType accountType;
    private Location location;

    /**
     * constructs a User object
     * @param username the username of the User
     * @param password the password of the User
     */
    @Ignore
    public User(String username, String password) {
        this("No Name", username, password, AccountType.USER);
    }

    /**
     * constructs a User object
     * @param name the name of the User
     * @param username the username of the User
     * @param password the password of the User
     * @param accountType the accountType of the User
     */
    @Ignore
    public User(String name, String username, String password, AccountType accountType) {
        this(name, username, password, accountType, (Location) null);
    }

    /**
     * constructs a User
     * @param name the name of the User
     * @param username the username of the User
     * @param password the password of the User
     * @param accountType the accountType of the User
     * @param location the location of the User
     */
    public User(String name, @NonNull String username, String password,
                AccountType accountType, Location location) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.location = location;
        if (location != null) {
            this.locationName = location.getLocationName();
        } else {
            this.locationName = "";
        }

    }

    /**
     * constructs a User
     * @param name the name of the User
     * @param username the username of the User
     * @param password the password of the User
     * @param accountType the accountType of the User
     * @param locationName the locationName of the User
     */
    @Ignore
    public User(String name, @NonNull String username, String password,
                AccountType accountType, String locationName) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.locationName = locationName;
    }

    /**
     * constructs a User
     */
    @Ignore
    public User(){

    }

    /**
     * getter for User's username
     * @return the username of the User
     */
    @NonNull public String getUsername() {
        return this.username;
    }



    /**
     * getter for User's password
     * @return the password of the User
     */
    public String getPassword() {
        return this.password;
    }



    /**
     * getter for User's name
     * @return name the name of the User
     */
    public String getName() { return this.name;}


    /**
     * getter for accountType of User
     * @return the accountType of the User
     */
    public AccountType getAccountType() {
        return accountType;
    }



    /**
     * getter for location of the User
     * @return the location of the User
     */
    public Location getLocation() { return location;}

    /**
     * setter for the location of the User
     * @param location the location of the User being set
     */
    public void setLocation(Location location) { this.location = location;}



    @Override
    public boolean equals(Object o) {
        if (o == null) { return false;}
        if (!(o instanceof User)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        String username1 = this.getUsername();
        String username2 = ((User) o).getUsername();
        return username1.equals(username2);

    }

}
