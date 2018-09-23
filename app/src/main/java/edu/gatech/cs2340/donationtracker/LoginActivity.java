package edu.gatech.cs2340.donationtracker;
import java.util.HashMap;

public class LoginActivity {
    private HashMap<String, String> userData = new HashMap<>();

    public void addUser(User user) {
        userData.put(user.getUsername(), user.getPassword());
    }
}
