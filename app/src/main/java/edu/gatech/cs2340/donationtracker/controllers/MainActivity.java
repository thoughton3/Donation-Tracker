package edu.gatech.cs2340.donationtracker.controllers;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.cs2340.donationtracker.model.AccountType;
import edu.gatech.cs2340.donationtracker.model.LoginActivity;
import edu.gatech.cs2340.donationtracker.R;
import edu.gatech.cs2340.donationtracker.model.User;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private Spinner accountTypeSpinner;
    private User user;

    @Override
    //start up
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomescreen);
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


}