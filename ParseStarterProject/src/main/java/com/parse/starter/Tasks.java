package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Tasks extends AppCompatActivity {

    private ParseObject project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        String projectId = getIntent().getExtras().getString("ProjectId");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Projects");
        query.getInBackground(projectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null && object != null) {
                    project = object;
                    setTitle(object.get("name").toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.tasks_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_task) {
            Intent addTaskIntent = new Intent(getApplicationContext(), AddTask.class);
            addTaskIntent.putExtra("ProjectId", project.getObjectId());
            startActivity(addTaskIntent);
        }
        else if (item.getItemId() == R.id.project_details) {
            Intent intent = new Intent(getApplicationContext(), ProjectDetails.class);
            intent.putExtra("ProjectId", project.getObjectId());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
