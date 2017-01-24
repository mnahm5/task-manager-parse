package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        setTitle("Add Project");
    }

    public void add_project(View view) {
        EditText etProjectName = (EditText) findViewById(R.id.etProjectName);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);

        ParseObject project = new ParseObject("Projects");
        project.put("username", ParseUser.getCurrentUser().getUsername());
        project.put("name", etProjectName.getText().toString());
        project.put("description", etDescription.getText().toString());
        project.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Project Saved",
                            Toast.LENGTH_SHORT
                    ).show();
                    Intent intent = new Intent(getApplicationContext(), Projects.class);
                    startActivity(intent);
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
}
