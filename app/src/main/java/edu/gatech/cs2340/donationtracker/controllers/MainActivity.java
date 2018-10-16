package edu.gatech.cs2340.donationtracker.controllers;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import edu.gatech.cs2340.donationtracker.model.AccountType;
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
    private User user;
    private ArrayList<Location> locations;
    private String[] names;
    public static String TAG = "MY_APP";



    @Override
    //start up
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomescreen);

        names = new String[6];
        Model model = Model.getInstance();
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



    }

    public void onLogin(View view) {
        username = (EditText) findViewById(R.id.editText4);
        password = (EditText) findViewById(R.id.editText3);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.Context), "Incorrect username and password combination", Snackbar.LENGTH_SHORT);
        if (username != null && password != null && LoginActivity.login(new User(username.getText().toString(), password.getText().toString()))) {
            user = new User(username.getText().toString(), "");
            String userName = LoginActivity.getName(user.getUsername());
            AccountType userAccountType = LoginActivity.getAccountType(user.getUsername());
            setContentView(R.layout.activity_main);
            TextView welcomeMessage = findViewById(R.id.textView);
            welcomeMessage.setText("Welcome " + userAccountType.getValue() + ": " + userName + "!");
        } else {
            snackbar.show();
        }
        ListView listView = findViewById(R.id.location_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener handler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.location_info);
                TextView info = findViewById(R.id.LocationInfo);
                info.setText(Model.get(position).toString());
            }
        };
        listView.setOnItemClickListener(handler);

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
        accountTypeSpinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, AccountType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
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
            if (LoginActivity.addUser(new User(name, username.getText().toString(), password.getText().toString(), (AccountType) accountTypeSpinner.getSelectedItem()))) {
                setContentView(R.layout.login);
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
                info.setText(Model.get(position).toString());
            }
        };
        listView.setOnItemClickListener(handler);
    }


}