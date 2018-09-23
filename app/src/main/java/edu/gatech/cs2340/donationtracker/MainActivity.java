package edu.gatech.cs2340.donationtracker;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    //start up
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.editText4);
        password = (EditText) findViewById(R.id.editText3);
        LoginActivity users = new LoginActivity();
        users.addUser(new User("Thomas", "H"));
    }

    public void onLogin(View view) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.Context), "Incorrect username and password combination", Snackbar.LENGTH_SHORT);
        if (LoginActivity.login(new User(username.getText().toString(), password.getText().toString()))) {
            setContentView(R.layout.activity_main);
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
}