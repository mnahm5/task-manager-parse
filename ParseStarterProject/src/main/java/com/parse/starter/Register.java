package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        redirectUser();
    }

    public void LoginLink(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void Register(final View view) {
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPassword2 = (EditText) findViewById(R.id.etPassword2);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etName = (EditText) findViewById(R.id.etFullName);
        final EditText etCompanyName = (EditText) findViewById(R.id.etCompanyName);

        if (etPassword.getText().toString().equals(etPassword2.getText().toString())) {
            ParseUser parseUser = new ParseUser();
            parseUser.setUsername(etUsername.getText().toString());
            parseUser.setEmail(etEmail.getText().toString());
            parseUser.setPassword(etPassword.getText().toString());
            parseUser.put("name", etName.getText().toString());
            parseUser.put("companyName", etCompanyName.getText().toString());
            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Account Created",
                                Toast.LENGTH_SHORT
                        ).show();
                        redirectUser();
                    }
                    else {
                        Toast.makeText(
                                getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void redirectUser() {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
    }
}
