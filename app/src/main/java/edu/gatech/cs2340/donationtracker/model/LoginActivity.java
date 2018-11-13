package edu.gatech.cs2340.donationtracker.model;
import android.arch.lifecycle.LiveData;
//import android.arch.persistence.room.Room;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity {
    private static HashMap<String, String> userData = new HashMap<>();
    private static ArrayList<User> userList = new ArrayList<>();
    public static String TAG = "MY_APP";

    public static boolean addUser(User user) {
        if (user == null) {
            return false;
        }
        if(userData.put(user.getUsername(), user.getPassword()) == null) {
            userList.add(user);
            return true;
        }
        return false;
    }

    public static void addUsers(List<User> users) {
        for (User user : users) {
            addUser(user);
        }
    }

    public static boolean login(User user) {
        if (userData.get(user.getUsername()) != null) {
            return (userData.get(user.getUsername()).equals(user.getPassword()));
        } else {
            return false;
        }
    }

    public static String getName(String username) {
        return userList.get(userList.indexOf(new User(username, ""))).getName();
    }

    public static AccountType getAccountType(String username) {
        return userList.get(userList.indexOf(new User(username, ""))).getAccountType();
    }

    public static Location getLocation(String username) {
        return userList.get(userList.indexOf((new User(username, "")))).getLocation();
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }
}
