package edu.gatech.cs2340.donationtracker.model;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity {
    private static HashMap<String, String> userData = new HashMap<>();
    private static ArrayList<User> userList = new ArrayList<>();

    public static boolean addUser(User user) {
        if(userData.put(user.getUsername(), user.getPassword()) == null) {
            userList.add(user);
            return true;
        }
        return false;
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
}
