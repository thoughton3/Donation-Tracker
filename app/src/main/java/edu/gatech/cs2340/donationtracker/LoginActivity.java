package edu.gatech.cs2340.donationtracker;
import java.util.HashMap;

public class LoginActivity {
    private static HashMap<String, String> userData = new HashMap<>();

    public void addUser(User user) {
        userData.put(user.getUsername(), user.getPassword());
    }

    public static boolean login(User user) {
        if (userData.get(user.getUsername()) != null) {
            return (userData.get(user.getUsername()).equals(user.getPassword()));
        } else {
            return false;
        }
    }
}
