package edu.gatech.cs2340.donationtracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.donationtracker.model.AccountType;
import edu.gatech.cs2340.donationtracker.model.Item;
import edu.gatech.cs2340.donationtracker.model.ItemType;
import edu.gatech.cs2340.donationtracker.model.Location;
import edu.gatech.cs2340.donationtracker.model.LoginActivity;
import edu.gatech.cs2340.donationtracker.model.Model;
import edu.gatech.cs2340.donationtracker.model.User;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * the tests for our Model class
 */
public class ModelTest {
    //String[] correctShortDescription = {"this is 0", "this is 1", "this is 2", "this is 3",
    // "this is 4", "this is 5", "this is 6", "this is 7", "this is 8", "this is 9"};
    final Location fakeLocation = new Location("Atlanta", "City",
            "33.7490° N", "84.3880° W", "1 ATL Street",
            "4044444444");
    final Item[] correctItem = {new Item("this is 0", "this is an item",
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
    final Model model = new Model();

    /**
     * a test for the addItems()  method
     */
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

    /**
     * a test for the addUser() method
     */
    @Test
    public void addUserTest() {
        User nullUser = null;
        User addedUser = new User("Thomas Houghton", "thoughton3@gmail.com",
                "password", AccountType.ADMIN, fakeLocation);
        User nonAddedUser = new User("Kate Vance", "kate.vance@gmail.com",
                "password", AccountType.USER, fakeLocation);
        assert(!LoginActivity.addUser(nullUser));
        assert(LoginActivity.getUserList().isEmpty());
        assert(LoginActivity.addUser(addedUser));
        assert(LoginActivity.getUserList().get(0).equals(addedUser));
        assert(LoginActivity.getUserList().size() == 1);
        assert(LoginActivity.addUser(nonAddedUser));
        assert(LoginActivity.getUserList().get(0).equals(addedUser));
        assert(LoginActivity.getUserList().get(1).equals(nonAddedUser));
        assert(LoginActivity.getUserList().size() == 2);
        assert(!LoginActivity.addUser(addedUser));
        assert(LoginActivity.getUserList().get(0).equals(addedUser));
        assert(LoginActivity.getUserList().get(1).equals(nonAddedUser));
        assert(LoginActivity.getUserList().size() == 2);
        LoginActivity.getUserList().clear();
    }

    /**
     * a test for the addUsers() method
     */
    @Test
    public void addUsersTest() {
        List<User> users = new ArrayList<>();
        User user1 = new User("Kate Vance", "kate.vance", "password",
                AccountType.ADMIN, fakeLocation);
        User user2 = new User("Thomas Houghton", "thoughton", "password",
                AccountType.USER, fakeLocation);
        User user3 = new User("Jessica Taetle", "jtaetle", "password",
                AccountType.LOCATION_EMPLOYEE, fakeLocation);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        LoginActivity.addUsers(users);
        for (int i = 0; i < users.size(); i++) {
            assert(LoginActivity.getUserList().get(i).equals(users.get(i)));
        }
        Iterable<User> emptyList = new ArrayList<>();
        LoginActivity.addUsers(emptyList);
        assert(LoginActivity.getUserList().size() == 3);
    }


//    @Test
//    public static boolean login(User user) {
//        Location fakeLocation = new Location("Atlanta", "City",
//                "33.7490° N", "84.3880° W", "1 ATL Street",
//                "4044444444");
//        User user1 = new User ("Jodi Dowis", "jdowis", "password", AccountType.USER,
// fakeLocation);
//
//        assert(LoginActivity.getUsername() == user1.getUsername());
//        assert(LoginActivity.getPassword() == user1.getPassword());
//    }
//
//    @After
//    public void onLogOut(View view) {
//        setContentView(R.layout.welcomescreen);
//    }
}
