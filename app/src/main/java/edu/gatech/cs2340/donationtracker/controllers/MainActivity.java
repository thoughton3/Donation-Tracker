package edu.gatech.cs2340.donationtracker.controllers;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ListAdapter;
import android.text.Editable;
import android.arch.persistence.room.RoomDatabase.Builder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import edu.gatech.cs2340.donationtracker.model.AccountType;
import edu.gatech.cs2340.donationtracker.model.AppDatabase;
import edu.gatech.cs2340.donationtracker.model.Dao;
import edu.gatech.cs2340.donationtracker.model.Item;
import edu.gatech.cs2340.donationtracker.model.ItemType;
import edu.gatech.cs2340.donationtracker.model.Location;
import edu.gatech.cs2340.donationtracker.model.LoginActivity;
import edu.gatech.cs2340.donationtracker.R;
import edu.gatech.cs2340.donationtracker.model.Model;
import edu.gatech.cs2340.donationtracker.model.User;

/**
 * this class represents the main activity of the app
 */
public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private Spinner accountTypeSpinner;
    private Spinner locationSpinner;
    private Spinner itemTypeSpinner;
    private String[] names;
    private User user;
    private static final String TAG = "MY_APP";
    private AppDatabase db;
    private boolean searchLocation;
    private boolean searchItems;
    private String searchString;
    private Location storeLocation;
    private Spinner searchSpinner;
    private Spinner categorySpinner;



    @Override
    //start up
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomescreen);
        names = new String[6];
        Builder<edu.gatech.cs2340.donationtracker.model.AppDatabase> builder =
                Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name");
        Builder<edu.gatech.cs2340.donationtracker.model.AppDatabase> allow =
                builder.allowMainThreadQueries();
        db = allow.build();

        try {
            Resources resources = getResources();
            InputStream is = resources.openRawResource(R.raw.location_data);

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            br.readLine();
            int num = 0;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                Model.addLocation((new Location(tokens[1], tokens[8],
                        tokens[3], tokens[2], tokens[4] + ", " + tokens[5] + ", " +
                        tokens[6] + ", " +  tokens[7], tokens[9])));
                names[num] = tokens[1];
                num++;
            }

        } catch(IOException e) {
            Log.e(MainActivity.TAG, "Error reading in location_data", e);
        }

        Dao dao = db.dao();
        LoginActivity.addUsers(dao.getAllUsers());
        Model.addItems(dao.getAllItems());



    }

    /**
     * this method determines what happens when the login button is pressed
     * @param view the current state of the app
     */
    public void onLogin(View view) {
        username = findViewById(R.id.editText4);
        password = findViewById(R.id.editText3);
        Editable usernameString = username.getText();
        Editable passwordString = password.getText();

        Snackbar snackbar = Snackbar.make(findViewById(R.id.Context),
                "Incorrect username and password combination", Snackbar.LENGTH_SHORT);
        if ((username != null) && (password != null) &&
                (LoginActivity.login(new User(usernameString.toString(),
                        passwordString.toString())))) {
            user = new User(usernameString.toString(), "");
            String userName = LoginActivity.getName(user.getUsername());
            AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
            if (userAccountType != AccountType.LOCATION_EMPLOYEE) {
                setContentView(R.layout.activity_main);
                TextView welcomeMessage = findViewById(R.id.textView);
                String text = "Welcome " + userAccountType.getValue() + ": " + userName + "!";
                welcomeMessage.setText(text);
                ListView listView = findViewById(R.id.location_list);

                ListAdapter adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, names);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        setContentView(R.layout.location_info);
                        TextView info = findViewById(R.id.LocationInfo);
                        Location location = Model.get(position);
                        info.setText(location.getLocationInfo());
                        storeLocation = Model.get(position);
                    }
                };
                listView.setOnItemClickListener(handler);
            } else {
                searchItems = false;
                Dao dao = db.dao();
                setContentView(R.layout.location_employee_page);
                ListView listView = findViewById(R.id.item_list);
                Location location = LoginActivity.getLocation(user.getUsername());
                final List<Item> locationItemList =
                        dao.getAllItemsFromLocation(location.getLocationName());
                final String[] locationItemNameList = new String[locationItemList.size()];
                for (int i = 0; i < locationItemList.size(); i++) {
                    Item item = locationItemList.get(i);
                    locationItemNameList[i] = item.getShortDescription();
                }
                ListAdapter adapter =
                        new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                                locationItemNameList);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        setContentView(R.layout.item_info);
                        TextView info = findViewById(R.id.item_info);
                        Item item = locationItemList.get(position);
                        String text = item.toString();
                        info.setText(text);
                    }
                };
                listView.setOnItemClickListener(handler);
                TextView welcomeMessage = findViewById(R.id.location_employee);
                String text = "Welcome " +
                        userAccountType.getValue() + " of " + location +
                        ": " + userName + "!";
                welcomeMessage.setText(text);

            }

        } else {
            snackbar.show();
        }


    }

    /**
     * when the cancel button is pressed the User is taken back to the welcome screen
     * @param view the current state of the app
     */
    public void onCancel(View view) {
        setContentView(R.layout.welcomescreen);
    }

    /**
     * when the logout button is pressed the User is taken back to the welcome screen
     * @param view the current state of the app
     */
    public void onLogOut(View view) {
        setContentView(R.layout.welcomescreen);
    }

    /**
     * transition from the welcome screen to the login screen
     * @param view the current state of the app
     */
    public void onWelcomeScreen(View view) {
        setContentView(R.layout.login);
    }

    /**
     * a User is brought to the registration page when they click the register button
     * @param view the current state of the app
     */
    //Register button on Home screen
    public void onRegisterNow(View view) {
        setContentView(R.layout.register);
        Model model = Model.getInstance();
        accountTypeSpinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                        AccountType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
        locationSpinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                        model.getLocationList());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter2);

    }

    /***
     * a user is registered when they press the register button
     * @param view the current state of the app
     */
    //Registers account passed in
    public void onRegisterAccount(View view) {
        username =  findViewById(R.id.editText5);
        password =  findViewById(R.id.editText6);
        firstName =  findViewById(R.id.editText);
        lastName =  findViewById(R.id.editText2);

        Editable first = firstName.getText();
        Editable last = lastName.getText();
        Editable usernameString = username.getText();
        Editable passwordString = password.getText();
        String name = first.toString() + " " + last.toString();
        Snackbar snackbar = Snackbar.make(findViewById(R.id.Context),
                "Please fill out ALL fields!", Snackbar.LENGTH_SHORT);
        if (!("").equals(usernameString.toString()) &&
                !("").equals(passwordString.toString()) &&
                !("").equals(first.toString()) &&
                !("").equals(last.toString())) {
            Snackbar snackbar2 = Snackbar.make(findViewById(R.id.Context),
                    "Username is already taken", Snackbar.LENGTH_SHORT);
            User loginUser;
            loginUser = new User(name, usernameString.toString(),
                    passwordString.toString(),
                    (AccountType) accountTypeSpinner.getSelectedItem(),
                    (Location) locationSpinner.getSelectedItem());
            if (LoginActivity.addUser(loginUser)) {
                final User loginUser2 = new User(name, usernameString.toString(),
                        passwordString.toString(),
                        (AccountType) accountTypeSpinner.getSelectedItem(),
                        (Location) locationSpinner.getSelectedItem());
                Dao dao = db.dao();
                dao.insertUser(loginUser2);



                setContentView(R.layout.login);
                loginUser.setLocation((Location) locationSpinner.getSelectedItem());
            } else {
                snackbar2.show();
            }
        } else {
            snackbar.show();
        }


    }

    /**
     * User is taken to the previous screen when they press the go back button
     * @param view the current state of the app
     */
    public void onGoBack(View view) {
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.location_list);

        ListAdapter adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.location_info);
                TextView info = findViewById(R.id.LocationInfo);
                Location location = Model.get(position);
                info.setText(location.getLocationInfo());
            }
        };
        listView.setOnItemClickListener(handler);
    }

    /**
     * when a location employee presses the logout button
     * @param view the current state of the app
     */
    public void onLogoutLocationEmployee(View view) {
        setContentView(R.layout.login);
    }

    /**
     * when a User refreshes the donation page
     * @param view the current state of the app
     */
    public void onUpdate(View view) {
        setContentView(R.layout.add_donation);
        itemTypeSpinner = findViewById(R.id.category);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                        ItemType.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypeSpinner.setAdapter(adapter2);
    }

    /**
     * when a User cancels the update they requested
     * @param view the current state of the app
     */
    public void onUpdateCancel(View view) {
        setContentView(R.layout.location_employee_page);
        ListView listView = findViewById(R.id.item_list);
        Location location = LoginActivity.getLocation(user.getUsername());
        Dao dao = db.dao();
        final List<Item> locationItemList =
                dao.getAllItemsFromLocation(location.getLocationName());
        final String[] locationItemNameList = new String[locationItemList.size()];
        for (int i = 0; i < locationItemList.size(); i++) {
            Item item = locationItemList.get(i);
            locationItemNameList[i] = item.getShortDescription();
        }
        ListAdapter adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                        locationItemNameList);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.item_info);
                TextView info = findViewById(R.id.item_info);
                Item item = locationItemList.get(position);
                info.setText(item.toString());
            }
        };
        listView.setOnItemClickListener(handler);
        String userName = LoginActivity.getName(user.getUsername());
        AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
        TextView welcomeMessage = findViewById(R.id.location_employee);
        String message = "Welcome " + userAccountType.getValue() +
                " of " + location + ": " + userName + "!";
        welcomeMessage.setText(message);

    }

    /**
     * when a User donates an item
     * @param view the current state of the app
     */
    public void onAddDonation(View view) {
        EditText shortDescription = findViewById(R.id.short_description);
        EditText fullDescription = findViewById(R.id.full_description);
        EditText value = findViewById(R.id.value);
        Spinner categories = findViewById(R.id.category);
        EditText comments = findViewById(R.id.comments);
        setContentView(R.layout.location_employee_page);
        final Location location = LoginActivity.getLocation(user.getUsername());
        Editable shortDesc = shortDescription.getText();
        Editable fullDesc = fullDescription.getText();
        Editable valueString = value.getText();
        Editable commentsString = comments.getText();
        Item item = new Item(shortDesc.toString(),
                fullDesc.toString(),
                Double.parseDouble(valueString.toString()),
                (ItemType) categories.getSelectedItem(), commentsString.toString(), location);
        Model.addItem(item);

        Dao dao = db.dao();
        dao.insertItem(item);



        final List<Item> locationItemList =
            dao.getAllItemsFromLocation(location.getLocationName());
        final String[] locationItemNameList = new String[locationItemList.size()];
        for (int i = 0; i < locationItemList.size(); i++) {
            Item item2 = locationItemList.get(i);
            locationItemNameList[i] = item2.getShortDescription();
        }
        ListAdapter adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                        locationItemNameList);

        ListView listView = findViewById(R.id.item_list);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchItems = false;
                setContentView(R.layout.item_info);
                TextView info = findViewById(R.id.item_info);
                Item item = locationItemList.get(position);
                info.setText(item.toString());
            }
        };
        listView.setOnItemClickListener(handler);
        String userName = LoginActivity.getName(user.getUsername());
        AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
        TextView welcomeMessage = findViewById(R.id.location_employee);
        String text = "Welcome " + userAccountType.getValue() + " of " +
                location + ": " + userName + "!";
        welcomeMessage.setText(text);
    }

    /**
     * when a location employee clicks on the back button
     * @param view the current state of the app
     */
    public void onGoBackLocationEmployee(View view) {
        if (searchItems) {
            setContentView(R.layout.searched_items);
            String[] itemResults;
            Snackbar snackbar = Snackbar.make(findViewById(R.id.Context2),
                    "No Results were found", Snackbar.LENGTH_SHORT);
            final List<Item> searchResults;
            if (!searchLocation) {
                if ("Search by Short Description".equals(searchSpinner.getSelectedItem())) {
                    Dao dao = db.dao();
                    searchResults = dao.getAllItemsByNameSearch(searchString);
                    if (searchResults.isEmpty()) {
                        Log.d(MainActivity.TAG, "DIDN'T WORK ...........................");
                        snackbar.show();
                    }
                    itemResults = new String[searchResults.size()];
                    for (int i = 0; i < searchResults.size(); i++) {
                        Item item = searchResults.get(i);
                        itemResults[i] = item.getShortDescription();
                    }
                } else {
                    Object spinnerSelected = categorySpinner.getSelectedItem();
                    Log.d(MainActivity.TAG, spinnerSelected.toString());
                    Object spinnerResults = categorySpinner.getSelectedItem();
                    Dao dao = db.dao();
                    searchResults = dao.getAllItemsByCategory(
                            spinnerResults.toString());
                    if (searchResults.isEmpty()) {
                        Log.d(MainActivity.TAG, "DIDN'T WORK ...........................");
                        snackbar.show();
                    }
                    itemResults = new String[searchResults.size()];
                    for (int i = 0; i < searchResults.size(); i++) {
                        Item item = searchResults.get(i);
                        itemResults[i] = item.getShortDescription();
                    }
                }


                ListView listView = findViewById(R.id.search_results);

                ListAdapter adapter =
                        new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                                itemResults);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        setContentView(R.layout.item_info);
                        TextView info = findViewById(R.id.item_info);
                        Item item = searchResults.get(position);
                        info.setText(item.toString());
                    }
                };
                listView.setOnItemClickListener(handler);
            } else {
                Log.d(MainActivity.TAG, storeLocation.getLocationName());
                if ("Search by Short Description".equals(searchSpinner.getSelectedItem())) {
                    Dao dao = db.dao();
                    searchResults = dao.getAllItemsAtLocationByNameSearch(
                            storeLocation.getLocationName(), searchString);
                    if (searchResults.isEmpty()) {
                        Log.d(MainActivity.TAG, "DIDN'T WORK ...........................");
                        snackbar.show();
                    }
                    itemResults = new String[searchResults.size()];
                    for (int i = 0; i < searchResults.size(); i++) {
                        Item item = searchResults.get(i);
                        itemResults[i] = item.getShortDescription();
                    }
                } else {
                    Object spinnerSelected = categorySpinner.getSelectedItem();
                    Dao dao = db.dao();
                    searchResults = dao.getAllItemsAtLocationByCategory(
                            storeLocation.getLocationName(),
                            spinnerSelected.toString());
                    if (searchResults.isEmpty()) {
                        Log.d(MainActivity.TAG, "DIDN'T WORK ...........................");
                        snackbar.show();
                    }
                    itemResults = new String[searchResults.size()];
                    for (int i = 0; i < searchResults.size(); i++) {
                        Item item = searchResults.get(i);
                        itemResults[i] = item.getShortDescription();
                    }
                }
                ListView listView = findViewById(R.id.search_results);

                ListAdapter adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, itemResults);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        setContentView(R.layout.item_info);
                        TextView info = findViewById(R.id.item_info);
                        Item item = searchResults.get(position);
                        info.setText(item.toString());
                    }
                };
                listView.setOnItemClickListener(handler);
            }
        } else {
            setContentView(R.layout.location_employee_page);
            ListView listView = findViewById(R.id.item_list);
            Location location = LoginActivity.getLocation(user.getUsername());
            Dao dao = db.dao();
            final List<Item> locationItemList =
                    dao.getAllItemsFromLocation(location.getLocationName());
            final String[] locationItemNameList = new String[locationItemList.size()];
            for (int i = 0; i < locationItemList.size(); i++) {
                Item item = locationItemList.get(i);
                locationItemNameList[i] = item.getShortDescription();
            }
            ListAdapter adapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                            locationItemNameList);


            listView.setAdapter(adapter);

            AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setContentView(R.layout.item_info);
                    TextView info = findViewById(R.id.item_info);
                    Item item = locationItemList.get(position);
                    info.setText(item.toString());
                }
            };
            listView.setOnItemClickListener(handler);
            String userName = LoginActivity.getName(user.getUsername());
            AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
            TextView welcomeMessage = findViewById(R.id.location_employee);
            String text = "Welcome " + userAccountType.getValue() + " of " +
                    location + ": " + userName + "!";
            welcomeMessage.setText(text);
        }
    }

    /**
     * when a User clicks on the search all button
     * @param view the current state of the app
     */
    //Search Stuff
    public void onSearchAll(View view) {
        searchLocation = false;
        setContentView(R.layout.search_item);
        String[] searchChoices = new String[2];
        searchChoices[0] = "Search by Short Description";
        searchChoices[1] = "Search by Category";
        searchSpinner = findViewById(R.id.search_spinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, searchChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);

        categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                        ItemType.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
    }

    /**
     * when a User clicks on the search location button
     * @param view the current state of the app
     */
    public void onSearchLocation(View view) {
        searchLocation = true;
        setContentView(R.layout.search_item);
        String[] searchChoices = new String[2];
        searchChoices[0] = "Search by Short Description";
        searchChoices[1] = "Search by Category";
        searchSpinner = findViewById(R.id.search_spinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, searchChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);

        categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                        ItemType.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
    }

    /**
     * when a User clicks on the search button
     * @param view the current state of the app
     */
    public void onSearch(View view) {
        searchItems = true;
        EditText searchText = findViewById(R.id.search_text);
        Editable search = searchText.getText();
        searchString = search.toString();
        Log.d(MainActivity.TAG, searchString + ".....................................");
        setContentView(R.layout.searched_items);
        String[] itemResults;
        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.Context2), "No Results were found",
                        Snackbar.LENGTH_SHORT);
        final List<Item> searchResults;
        if (!searchLocation) {
            if ("Search by Short Description".equals("Search by Short Description")) {
                Dao dao = db.dao();
                searchResults = dao.getAllItemsByNameSearch(searchString);
                if (searchResults.isEmpty()) {
                    Log.d(MainActivity.TAG, "DIDN'T WORK ...........................");
                    snackbar.show();
                }
                itemResults = new String[searchResults.size()];
                for (int i = 0; i < searchResults.size(); i++) {
                    Item item = searchResults.get(i);
                    itemResults[i] = item.getShortDescription();
                }
            } else {
                Object selectedSpinner = categorySpinner.getSelectedItem();
                Log.d(MainActivity.TAG, selectedSpinner.toString());
                Object selectedSpinner2 = categorySpinner.getSelectedItem();
                Dao dao = db.dao();
                searchResults = dao.getAllItemsByCategory(
                        selectedSpinner2.toString());
                if (searchResults.isEmpty()) {
                    Log.d(MainActivity.TAG, "DIDN'T WORK ...........................");
                    snackbar.show();
                }
                itemResults = new String[searchResults.size()];
                for (int i = 0; i < searchResults.size(); i++) {
                    Item item = searchResults.get(i);
                    itemResults[i] = item.getShortDescription();
                }
            }


            ListView listView = findViewById(R.id.search_results);

            ListAdapter adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, itemResults);

            listView.setAdapter(adapter);

            AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setContentView(R.layout.item_info);
                    TextView info = findViewById(R.id.item_info);
                    Item item = searchResults.get(position);
                    info.setText(item.toString());
                }
            };
            listView.setOnItemClickListener(handler);
        } else {
            Log.d(MainActivity.TAG, storeLocation.getLocationName());
            if ("Search by Short Description".equals(searchSpinner.getSelectedItem())) {
                Dao dao = db.dao();
                searchResults =
                        dao.getAllItemsAtLocationByNameSearch(storeLocation.getLocationName(),
                                searchString);
                if (searchResults.isEmpty()) {
                    Log.d(MainActivity.TAG, "DIDN'T WORK ...........................");
                    snackbar.show();
                }
                itemResults = new String[searchResults.size()];
                for (int i = 0; i < searchResults.size(); i++) {
                    Item item = searchResults.get(i);
                    itemResults[i] = item.getShortDescription();
                }
            } else {
                Object selectedSpinner = categorySpinner.getSelectedItem();
                Dao dao = db.dao();
                searchResults =
                        dao.getAllItemsAtLocationByCategory(storeLocation.getLocationName(),
                                selectedSpinner.toString());
                if (searchResults.isEmpty()) {
                    Log.d(MainActivity.TAG, "DIDN'T WORK ...........................");
                    snackbar.show();
                }
                itemResults = new String[searchResults.size()];
                for (int i = 0; i < searchResults.size(); i++) {
                    Item item = searchResults.get(i);
                    itemResults[i] = item.getShortDescription();
                }
            }
            ListView listView = findViewById(R.id.search_results);

            ListAdapter adapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                            itemResults);

            listView.setAdapter(adapter);

            AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setContentView(R.layout.item_info);
                    TextView info = findViewById(R.id.item_info);
                    Item item = searchResults.get(position);
                    info.setText(item.toString());
                }
            };
            listView.setOnItemClickListener(handler);
        }
    }

    /**
     * when a User clicks on the back button while in the search screen
     * @param view the current state of the screen
     */
    public void onSearchGoBack(View view) {
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.location_list);

        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.location_info);
                TextView info = findViewById(R.id.LocationInfo);
                Location location = Model.get(position);
                info.setText(location.getLocationInfo());
                storeLocation = Model.get(position);
            }
        };
        listView.setOnItemClickListener(handler);
    }

    /**
     * when a User had searched for something and then they click the back button
     * @param view the current state of the screen
     */
    public void onSearchedGoBack(View view) {
        setContentView(R.layout.search_item);
        String[] searchChoices = new String[2];
        searchChoices[0] = "Search by Short Description";
        searchChoices[1] = "Search by Category";
        searchSpinner = findViewById(R.id.search_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, searchChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);

        categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, ItemType.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
    }

    /**
     * when a User clicks on the map button
     * @param view the current state of the app
     */
    public void onMapClick(View view) {
        Intent map = new Intent(this, MapsActivity.class);
        startActivity(map);
    }





}