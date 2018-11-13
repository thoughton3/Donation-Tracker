package edu.gatech.cs2340.donationtracker.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;

import edu.gatech.cs2340.donationtracker.controllers.MainActivity;

@Entity(tableName = "users")
public class User {
    @Ignore
    private static String TAG = "My_App";
    @ColumnInfo(name = "name")
    private String name;
    @PrimaryKey

    @ColumnInfo(name = "username")
    @NonNull private String username = "";
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "account_type")
    private AccountType accountType;
    @Ignore
    private String accountTypeString;
    private Location location;
    @Ignore
    private String locationName;

    @Ignore
    public User(String username, String password) {
        this("No Name", username, password, AccountType.USER);
    }
    @Ignore
    public User(String name, String username, String password, AccountType accountType) {
        this(name, username, password, accountType, (Location) null);
    }

    public User(String name, @NonNull String username, String password, AccountType accountType, Location location) {
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
    @Ignore
    public User(String name, @NonNull String username, String password, AccountType accountType, String locationName) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.locationName = locationName;
    }

    @Ignore
    public User(){

    }

    public @NonNull String getUsername() {
        return this.username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() { return this.name;}

    public void setName(String name) { this.name = name;}

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Location getLocation() { return location;}

    public void setLocation(Location location) { this.location = location;}

    public String getAccountTypeString() {
        return accountTypeString;
    }

    public void setAccountTypeString(String accountTypeString) {
        this.accountTypeString = accountTypeString;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false;}
        if (!(o instanceof User)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        return this.getUsername().equals(((User) o).getUsername());

    }

}
