package com.parse.starter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class ProjectDetails extends AppCompatActivity {

    private boolean flag = false;
    private ParseObject project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        final EditText etProjectName = (EditText) findViewById(R.id.etProjectName);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);

        String projectId = getIntent().getExtras().getString("ProjectId");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Projects");
        query.getInBackground(projectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null && object != null) {
                    project = object;
                    setTitle(object.get("name").toString());
                    etProjectName.setText(object.get("name").toString());
                    etDescription.setText(object.get("description").toString());
                }
            }
        });
    }

    public void edit_project(View view) {
        final EditText etProjectName = (EditText) findViewById(R.id.etProjectName);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);

        if (flag) {
            project.put("name", etProjectName.getText().toString());
            project.put("description", etDescription.getText().toString());
            project.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Project Updated",
                                Toast.LENGTH_SHORT
                        ).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Update Failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            });
        }
        else {
            Button btEdit = (Button) findViewById(R.id.btEdit);
            btEdit.setText(R.string.save_changes);
            etProjectName.setEnabled(true);
            etDescription.setEnabled(true);
            flag = true;
        }
    }

    public void delete(View view) {

    }
}
