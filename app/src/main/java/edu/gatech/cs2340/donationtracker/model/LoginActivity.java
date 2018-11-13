package edu.gatech.cs2340.donationtracker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * this class represents all of the activity on the login page of the app
 */
public class LoginActivity {
    private static final Map<String, String> userData = new HashMap<>();
    private static final List<User> userList = new ArrayList<>();

    /**
     * adds a User to the database
     * @param user the User being added
     * @return a boolean of whether or not the User was successfully added
     */
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

    /**
     * adds a list of Users to the database
     * @param users list of Users
     */
    public static void addUsers(Iterable<User> users) {
        for (User user : users) {
            addUser(user);
        }
    }

    /**
     * logs a User in to the app
     * @param user the User being logged in
     * @return a boolean of whether or not the User was successfully logged in
     */
    public static boolean login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String username2 = userData.get(username);
        return username2.equals(password);
    }

    /**
     * getter for the name of the User
     * @param username the username of the User
     * @return the name of the User
     */
    public static String getName(String username) {
        int index = userList.indexOf(new User(username, ""));
        User user = userList.get(index);
        return user.getName();
    }

    /**
     * getter for the accountType of a User
     * @param username the username of the User
     * @return the accountType of the User
     */
    public static AccountType getAccountType(String username) {
        int index = userList.indexOf(new User(username, ""));
        User user = userList.get(index);
        return user.getAccountType();
    }

    /**
     * getter for the location of the User
     * @param username the username of the User
     * @return the location of the User
     */
    public static Location getLocation(String username) {
        int index = userList.indexOf(new User(username, ""));
        User user = userList.get(index);
        return user.getLocation();
    }

    /**
     * getter for the list of Users
     * @return the list of Users
     */
    public static List<User> getUserList() {
        return userList;
    }
}
