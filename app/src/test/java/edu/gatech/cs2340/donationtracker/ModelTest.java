package edu.gatech.cs2340.donationtracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.gatech.cs2340.donationtracker.model.AccountType;
import edu.gatech.cs2340.donationtracker.model.Item;
import edu.gatech.cs2340.donationtracker.model.ItemType;
import edu.gatech.cs2340.donationtracker.model.Location;
import edu.gatech.cs2340.donationtracker.model.LoginActivity;
import edu.gatech.cs2340.donationtracker.model.Model;
import edu.gatech.cs2340.donationtracker.model.User;

import static org.junit.Assert.*;
public class ModelTest {
    //String[] correctShortDescription = {"this is 0", "this is 1", "this is 2",
    // "this is 3", "this is 4", "this is 5", "this is 6", "this is 7", "this is 8", "this is 9"};
    private final Location fakeLocation = new Location("Atlanta", "City",
            "33.7490° N", "84.3880° W", "1 ATL Street",
            "4044444444");
    private final Item[] correctItem = {new Item("this is 0", "this is an item",
            0.0, ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 1", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 2", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 3", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 4", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 5", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 6", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 7", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 8", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation),
            new Item("this is 9", "this is an item", 0.0,
                    ItemType.CLOTHING, "this is a comment", fakeLocation)};
    private final Model model = new Model();
    @Test
    public void addItemsTest() {
        Collection<Item> testList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double ii = (double) i;
            Item item = new Item("this is "+ Integer.toString(i),
                    "this is an item", 0.0, ItemType.CLOTHING,
                    "this is a comment", fakeLocation);
            testList.add(item);
        }
        assertNotNull(testList);
        model.addItems(testList);
        for (int i = 0; i < model.getItemListArray().length; i++) {
            assertEquals(correctItem[i], model.getItem(i));

        }
    }

    @Test
    public void addUserTest() {
        User nullUser = null;
        User addedUser = new User("Thomas Houghton", "thoughton3@gmail.com",
                "password", AccountType.ADMIN, fakeLocation);
        User nonAddedUser = new User("Kate Vance", "kate.vance@gmail.com",
                "password", AccountType.USER, fakeLocation);
        List<User> userList = LoginActivity.getUserList();
        User user0 = userList.get(0);
        User user1 = userList.get(1);
        assert(LoginActivity.addUser(addedUser));
        assert(user0.equals(addedUser));
        assert(userList.size() == 1);
        assert(LoginActivity.addUser(nonAddedUser));
        assert(user0.equals(addedUser));
        assert(user1.equals(nonAddedUser));
        assert(userList.size() == 2);
        assert(!LoginActivity.addUser(addedUser));
        assert(user0.equals(addedUser));
        assert(user1.equals(nonAddedUser));
        assert(userList.size() == 2);
        userList.clear();
    }

    @Test
    public void addUsersTest() {
        List<User> users = new ArrayList<>();
        User user1 = new User("Kate Vance", "kate.vance", "password",
                AccountType.ADMIN, fakeLocation);
        User user2 = new User("Thomas Houghton", "thoughton",
                "password", AccountType.USER, fakeLocation);
        User user3 = new User("Jessica Taetle", "jtaetle", "password",
                AccountType.LOCATION_EMPLOYEE, fakeLocation);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        LoginActivity.addUsers(users);
        for (int i = 0; i < users.size(); i++) {
            List<User> userList = LoginActivity.getUserList();
            User user_i = userList.get(i);
            assert(user_i.equals(users.get(i)));
        }
        Iterable<User> emptyList = new ArrayList<>();
        LoginActivity.addUsers(emptyList);
        List<User> userList = LoginActivity.getUserList();
        assert(userList.size() == 3);
    }

    @Test
    public void getLocationStringListTest() {
        List<Location> locations = new ArrayList<>();
        Location one = new Location("Atlanta", "City",
                "33.7490° N", "84.3880° W", "310 10th Street NW",
                "9145528754");
        Location two = new Location("New York", "City",
                "45.3333° N", "90.4555° W", "530 Shore Acres Drive",
                "9145528754");
        Location three = new Location("Microsoft", "Headquarters",
                "45.0000", "50.0000", "One Microsoft Way ",
                "45673428889");
        Model.addLocation(one);
        Model.addLocation(two);
        Model.addLocation(three);
        locations.add(one);
        locations.add(two);
        locations.add(three);
        for (int i = 0; i < locations.size(); i++) {
            assert(Model.getLocationStringList().get(i).equals(locations.get(i).getLocationName()));
        }
        assert(Model.getLocationStringList().size() == 3);
    }
}