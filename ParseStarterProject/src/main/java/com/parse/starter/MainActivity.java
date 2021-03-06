/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
        redirectUser();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void RegisterLink(View view) {
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }

    public void Login(View view) {
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.i("Info", "Logged In");
                    redirectUser();
                }
                else {
                    Toast.makeText(
                            getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void redirectUser() {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
    }
}
