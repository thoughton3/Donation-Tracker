package edu.gatech.cs2340.donationtracker.controllers;

//import android.arch.persistence.room.Room;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.SettingsSlicesContract;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.gatech.cs2340.donationtracker.model.AccountType;
import edu.gatech.cs2340.donationtracker.model.AppDatabase;
import edu.gatech.cs2340.donationtracker.model.DonationTrackerRepository;
import edu.gatech.cs2340.donationtracker.model.Item;
import edu.gatech.cs2340.donationtracker.model.ItemType;
import edu.gatech.cs2340.donationtracker.model.Location;
import edu.gatech.cs2340.donationtracker.model.LoginActivity;
import edu.gatech.cs2340.donationtracker.R;
import edu.gatech.cs2340.donationtracker.model.Model;
import edu.gatech.cs2340.donationtracker.model.User;

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
    public static String TAG = "MY_APP";
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
        Model model = Model.getInstance();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        try {
            InputStream is = getResources().openRawResource(R.raw.location_data);

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            br.readLine();
            int num = 0;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                Model.addLocation((new Location(tokens[1], tokens[8], tokens[3], tokens[2], tokens[4] + ", " + tokens[5] + ", " + tokens[6] + ", " +  tokens[7], tokens[9])));
                names[num] = tokens[1];
                num++;
            }

        } catch(IOException e) {
            Log.e(MainActivity.TAG, "Error reading in location_data", e);
        }

        LoginActivity.addUsers(db.doa().getAllUsers());
        if (db.doa().getAllUsers() == null) {
            Log.d(MainActivity.TAG, "EMPTY STILL..................");
        }
        Model.addItems(db.doa().getAllItems());



    }

    public void onLogin(View view) {
        username = (EditText) findViewById(R.id.editText4);
        password = (EditText) findViewById(R.id.editText3);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.Context), "Incorrect username and password combination", Snackbar.LENGTH_SHORT);
        if (username != null && password != null && LoginActivity.login(new User(username.getText().toString(), password.getText().toString()))) {
            user = new User(username.getText().toString(), "");
            String userName = LoginActivity.getName(user.getUsername());
            AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
            if (userAccountType != AccountType.LOCATION_EMPLOYEE) {
                setContentView(R.layout.activity_main);
                TextView welcomeMessage = findViewById(R.id.textView);
                welcomeMessage.setText("Welcome " + userAccountType.getValue() + ": " + userName + "!");
                ListView listView = findViewById(R.id.location_list);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        setContentView(R.layout.location_info);
                        TextView info = findViewById(R.id.LocationInfo);
                        info.setText(Model.get(position).getLocationInfo());
                        storeLocation = Model.get(position);
                    }
                };
                listView.setOnItemClickListener(handler);
            } else {
                searchItems = false;
                setContentView(R.layout.location_employee_page);
                ListView listView = findViewById(R.id.item_list);
                Location location = LoginActivity.getLocation(user.getUsername());
                final List<Item> locationItemList = db.doa().getAllItemsFromLocation(location.getLocationName());
                final String[] locationItemNameList = new String[locationItemList.size()];
                for (int i = 0; i < locationItemList.size(); i++) {
                    locationItemNameList[i] = locationItemList.get(i).getShortDescription();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationItemNameList);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        setContentView(R.layout.item_info);
                        TextView info = findViewById(R.id.item_info);
                        info.setText(locationItemList.get(position).toString());
                    }
                };
                listView.setOnItemClickListener(handler);
                TextView welcomeMessage = findViewById(R.id.location_employee);
                welcomeMessage.setText("Welcome " + userAccountType.getValue() + " of " + location + ": " + userName + "!");

            }

        } else {
            snackbar.show();
        }


    }

    public void onCancel(View view) {
        setContentView(R.layout.welcomescreen);
    }

    public void onLogOut(View view) {
        setContentView(R.layout.welcomescreen);
    }

    public void onWelcomeScreen(View view) {
        setContentView(R.layout.login);
    }

    //Register button on Home screen
    public void onRegisterNow(View view) {
        setContentView(R.layout.register);
        Model model = Model.getInstance();
        accountTypeSpinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, AccountType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
        locationSpinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, model.getLocationList());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter2);

    }

    //Registers account passed in
    public void onRegisterAccount(View view) {
        username = (EditText) findViewById(R.id.editText5);
        password = (EditText) findViewById(R.id.editText6);
        firstName = (EditText) findViewById(R.id.editText);
        lastName = (EditText) findViewById(R.id.editText2);

        String name = firstName.getText().toString() + " " + lastName.getText().toString();
        Snackbar snackbar = Snackbar.make(findViewById(R.id.Context), "Please fill out ALL fields!", Snackbar.LENGTH_SHORT);
        if (!username.getText().toString().equals("") && !password.getText().toString().equals("") && !firstName.getText().toString().equals("") && !lastName.getText().toString().equals("")) {
            Snackbar snackbar2 = Snackbar.make(findViewById(R.id.Context), "Username is already taken", Snackbar.LENGTH_SHORT);
            User loginUser;
            loginUser = new User(name, username.getText().toString(), password.getText().toString(), (AccountType) accountTypeSpinner.getSelectedItem(), (Location) locationSpinner.getSelectedItem());
            if (LoginActivity.addUser(loginUser)) {
                final User loginUser2 = new User(name, username.getText().toString(), password.getText().toString(), (AccountType) accountTypeSpinner.getSelectedItem(), (Location) locationSpinner.getSelectedItem());
                db.doa().insertUser(loginUser2);



                setContentView(R.layout.login);
                loginUser.setLocation((Location) locationSpinner.getSelectedItem());
            } else {
                snackbar2.show();
            }
        } else {
            snackbar.show();
        }


    }

    public void onGoBack(View view) {
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.location_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.location_info);
                TextView info = findViewById(R.id.LocationInfo);
                info.setText(Model.get(position).getLocationInfo());
            }
        };
        listView.setOnItemClickListener(handler);
    }

    public void onLogoutLocationEmployee(View view) {
        setContentView(R.layout.login);
    }

    public void onUpdate(View view) {
        setContentView(R.layout.add_donation);
        itemTypeSpinner = findViewById(R.id.category);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ItemType.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypeSpinner.setAdapter(adapter2);
    }
    public void onUpdateCancel(View view) {
        setContentView(R.layout.location_employee_page);
        ListView listView = findViewById(R.id.item_list);
        Location location = LoginActivity.getLocation(user.getUsername());
        final List<Item> locationItemList = db.doa().getAllItemsFromLocation(location.getLocationName());
        final String[] locationItemNameList = new String[locationItemList.size()];
        for (int i = 0; i < locationItemList.size(); i++) {
            locationItemNameList[i] = locationItemList.get(i).getShortDescription();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationItemNameList);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.item_info);
                TextView info = findViewById(R.id.item_info);
                info.setText(locationItemList.get(position).toString());
            }
        };
        listView.setOnItemClickListener(handler);
        String userName = LoginActivity.getName(user.getUsername());
        AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
        TextView welcomeMessage = findViewById(R.id.location_employee);
        welcomeMessage.setText("Welcome " + userAccountType.getValue() + " of " + location + ": " + userName + "!");

    }

    public void onAddDonation(View view) {
        EditText shortDescription = findViewById(R.id.short_description);
        EditText fullDescription = findViewById(R.id.full_description);
        EditText value = findViewById(R.id.value);
        Spinner categories = findViewById(R.id.category);
        EditText comments = findViewById(R.id.comments);
        setContentView(R.layout.location_employee_page);
        Location location = LoginActivity.getLocation(user.getUsername());
        Item item = new Item(shortDescription.getText().toString(), fullDescription.getText().toString(), Double.parseDouble(value.getText().toString()), (ItemType) categories.getSelectedItem(), comments.getText().toString(), location);
        Model.addItem(item);

        db.doa().insertItem(item);



        final List<Item> locationItemList = db.doa().getAllItemsFromLocation(location.getLocationName());
        final String[] locationItemNameList = new String[locationItemList.size()];
        for (int i = 0; i < locationItemList.size(); i++) {
            locationItemNameList[i] = locationItemList.get(i).getShortDescription();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationItemNameList);

        ListView listView = findViewById(R.id.item_list);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchItems = false;
                setContentView(R.layout.item_info);
                TextView info = findViewById(R.id.item_info);
                info.setText(locationItemList.get(position).toString());
            }
        };
        listView.setOnItemClickListener(handler);
        String userName = LoginActivity.getName(user.getUsername());
        AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
        TextView welcomeMessage = findViewById(R.id.location_employee);
        welcomeMessage.setText("Welcome " + userAccountType.getValue() + " of " + location + ": " + userName + "!");
    }

    public void onGoBackLocationEmployee(View view) {
        if (searchItems) {
            setContentView(R.layout.searched_items);
            String[] itemResults;
            Snackbar snackbar = Snackbar.make(findViewById(R.id.Context2), "No Results were found", Snackbar.LENGTH_SHORT);
            final List<Item> searchResults;
            if (!searchLocation) {
                if (searchSpinner.getSelectedItem().equals("Search by Short Description")) {
                    searchResults = db.doa().getAllItemsByNameSearch(searchString);
                    if (searchResults.size() == 0) {
                        Log.d(MainActivity.TAG, "DIDNT WORK ...........................");
                        snackbar.show();
                    }
                    itemResults = new String[searchResults.size()];
                    for (int i = 0; i < searchResults.size(); i++) {
                        itemResults[i] = searchResults.get(i).getShortDescription();
                    }
                } else {
                    Log.d(MainActivity.TAG, categorySpinner.getSelectedItem().toString());
                    searchResults = db.  doa().getAllItemsByCategory((String)categorySpinner.getSelectedItem().toString());
                    if (searchResults.size() == 0) {
                        Log.d(MainActivity.TAG, "DIDNT WORK ...........................");
                        snackbar.show();
                    }
                    itemResults = new String[searchResults.size()];
                    for (int i = 0; i < searchResults.size(); i++) {
                        itemResults[i] = searchResults.get(i).getShortDescription();
                    }
                }


                ListView listView = findViewById(R.id.search_results);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemResults);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        setContentView(R.layout.item_info);
                        TextView info = findViewById(R.id.item_info);
                        info.setText(searchResults.get(position).toString());
                    }
                };
                listView.setOnItemClickListener(handler);
            } else {
                Log.d(MainActivity.TAG, storeLocation.getLocationName());
                if (searchSpinner.getSelectedItem().equals("Search by Short Description")) {
                    searchResults = db.doa().getAllItemsAtLocationByNameSearch(storeLocation.getLocationName(), searchString);
                    if (searchResults.size() == 0) {
                        Log.d(MainActivity.TAG, "DIDNT WORK ...........................");
                        snackbar.show();
                    }
                    itemResults = new String[searchResults.size()];
                    for (int i = 0; i < searchResults.size(); i++) {
                        itemResults[i] = searchResults.get(i).getShortDescription();
                    }
                } else {
                    searchResults = db.doa().getAllItemsAtLocationByCategory(storeLocation.getLocationName(), (String)categorySpinner.getSelectedItem().toString());
                    if (searchResults.size() == 0) {
                        Log.d(MainActivity.TAG, "DIDNT WORK ...........................");
                        snackbar.show();
                    }
                    itemResults = new String[searchResults.size()];
                    for (int i = 0; i < searchResults.size(); i++) {
                        itemResults[i] = searchResults.get(i).getShortDescription();
                    }
                }
                ListView listView = findViewById(R.id.search_results);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemResults);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        setContentView(R.layout.item_info);
                        TextView info = findViewById(R.id.item_info);
                        info.setText(searchResults.get(position).toString());
                    }
                };
                listView.setOnItemClickListener(handler);
            }
        } else {
            setContentView(R.layout.location_employee_page);
            ListView listView = findViewById(R.id.item_list);
            Location location = LoginActivity.getLocation(user.getUsername());
            final List<Item> locationItemList = db.doa().getAllItemsFromLocation(location.getLocationName());
            final String[] locationItemNameList = new String[locationItemList.size()];
            for (int i = 0; i < locationItemList.size(); i++) {
                locationItemNameList[i] = locationItemList.get(i).getShortDescription();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationItemNameList);


            listView.setAdapter(adapter);

            AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setContentView(R.layout.item_info);
                    TextView info = findViewById(R.id.item_info);
                    info.setText(locationItemList.get(position).toString());
                }
            };
            listView.setOnItemClickListener(handler);
            String userName = LoginActivity.getName(user.getUsername());
            AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
            TextView welcomeMessage = findViewById(R.id.location_employee);
            welcomeMessage.setText("Welcome " + userAccountType.getValue() + " of " + location + ": " + userName + "!");
        }
    }

    //Search Stuff
    public void onSearchAll(View view) {
        searchLocation = false;
        setContentView(R.layout.search_item);
        String[] searchChoices = new String[2];
        searchChoices[0] = "Search by Short Description";
        searchChoices[1] = "Search by Category";
        searchSpinner = findViewById(R.id.search_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, searchChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);

        categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ItemType.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
    }

    public void onSearchLocation(View view) {
        searchLocation = true;
        setContentView(R.layout.search_item);
        String[] searchChoices = new String[2];
        searchChoices[0] = "Search by Short Description";
        searchChoices[1] = "Search by Category";
        searchSpinner = findViewById(R.id.search_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, searchChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);

        categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ItemType.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
    }

    public void onSearch(View view) {
        searchItems = true;
        EditText searchText = findViewById(R.id.search_text);
        searchString = searchText.getText().toString();
        Log.d(MainActivity.TAG, searchString + ".....................................");
        setContentView(R.layout.searched_items);
        String[] itemResults;
        Snackbar snackbar = Snackbar.make(findViewById(R.id.Context2), "No Results were found", Snackbar.LENGTH_SHORT);
        final List<Item> searchResults;
        if (!searchLocation) {
            if (searchSpinner.getSelectedItem().equals("Search by Short Description")) {
                searchResults = db.doa().getAllItemsByNameSearch(searchString);
                if (searchResults.size() == 0) {
                    Log.d(MainActivity.TAG, "DIDNT WORK ...........................");
                    snackbar.show();
                }
                itemResults = new String[searchResults.size()];
                for (int i = 0; i < searchResults.size(); i++) {
                    itemResults[i] = searchResults.get(i).getShortDescription();
                }
            } else {
                Log.d(MainActivity.TAG, categorySpinner.getSelectedItem().toString());
                searchResults = db.  doa().getAllItemsByCategory((String)categorySpinner.getSelectedItem().toString());
                if (searchResults.size() == 0) {
                    Log.d(MainActivity.TAG, "DIDNT WORK ...........................");
                    snackbar.show();
                }
                itemResults = new String[searchResults.size()];
                for (int i = 0; i < searchResults.size(); i++) {
                    itemResults[i] = searchResults.get(i).getShortDescription();
                }
            }


            ListView listView = findViewById(R.id.search_results);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemResults);

            listView.setAdapter(adapter);

            AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setContentView(R.layout.item_info);
                    TextView info = findViewById(R.id.item_info);
                    info.setText(searchResults.get(position).toString());
                }
            };
            listView.setOnItemClickListener(handler);
        } else {
            Log.d(MainActivity.TAG, storeLocation.getLocationName());
            if (searchSpinner.getSelectedItem().equals("Search by Short Description")) {
                searchResults = db.doa().getAllItemsAtLocationByNameSearch(storeLocation.getLocationName(), searchString);
                if (searchResults.size() == 0) {
                    Log.d(MainActivity.TAG, "DIDNT WORK ...........................");
                    snackbar.show();
                }
                itemResults = new String[searchResults.size()];
                for (int i = 0; i < searchResults.size(); i++) {
                    itemResults[i] = searchResults.get(i).getShortDescription();
                }
            } else {
                searchResults = db.doa().getAllItemsAtLocationByCategory(storeLocation.getLocationName(), (String)categorySpinner.getSelectedItem().toString());
                if (searchResults.size() == 0) {
                    Log.d(MainActivity.TAG, "DIDNT WORK ...........................");
                    snackbar.show();
                }
                itemResults = new String[searchResults.size()];
                for (int i = 0; i < searchResults.size(); i++) {
                    itemResults[i] = searchResults.get(i).getShortDescription();
                }
            }
            ListView listView = findViewById(R.id.search_results);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemResults);

            listView.setAdapter(adapter);

            AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setContentView(R.layout.item_info);
                    TextView info = findViewById(R.id.item_info);
                    info.setText(searchResults.get(position).toString());
                }
            };
            listView.setOnItemClickListener(handler);
        }
    }

    public void onSearchGoBack(View view) {
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.location_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.location_info);
                TextView info = findViewById(R.id.LocationInfo);
                info.setText(Model.get(position).getLocationInfo());
                storeLocation = Model.get(position);
            }
        };
        listView.setOnItemClickListener(handler);
    }

    public void onSearchedGoBack(View view) {
        setContentView(R.layout.search_item);
        String[] searchChoices = new String[2];
        searchChoices[0] = "Search by Short Description";
        searchChoices[1] = "Search by Category";
        searchSpinner = findViewById(R.id.search_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, searchChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);

        categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ItemType.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
    }

    public void onMapClick(View view) {
        Intent map = new Intent(this, MapsActivity.class);
        startActivity(map);
    }





}