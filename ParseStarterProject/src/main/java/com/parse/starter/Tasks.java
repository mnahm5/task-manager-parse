package com.parse.starter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

public class Tasks extends AppCompatActivity implements TaskFeed.OnFragmentInteractionListener {

    private ParseObject project;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

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

        Bundle bundle = new Bundle();

        ArrayList<String> projectIds = new ArrayList<String>();
        projectIds.add(projectId);
        bundle.putStringArrayList("userProjectIds", projectIds);

        TaskFeed taskFeed = new TaskFeed();
        taskFeed.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.taskFeed, taskFeed);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
            Intent projectDetailsIntent = new Intent(getApplicationContext(), ProjectDetails.class);
            projectDetailsIntent.putExtra("ProjectId", project.getObjectId());
            startActivity(projectDetailsIntent);
        }
        else if (item.getItemId() == R.id.to_do)
        {
            Intent toDoIntent = new Intent(getApplicationContext(), SpecificListTasks.class);
            toDoIntent.putExtra("ProjectId", project.getObjectId());
            toDoIntent.putExtra("TaskType", "To Do");
            startActivity(toDoIntent);
        }
        else if (item.getItemId() == R.id.doing)
        {
            Intent doingIntent = new Intent(getApplicationContext(), SpecificListTasks.class);
            doingIntent.putExtra("ProjectId", project.getObjectId());
            doingIntent.putExtra("TaskType", "Doing");
            startActivity(doingIntent);
        }
        else if (item.getItemId() == R.id.done)
        {
            Intent doneIntent = new Intent(getApplicationContext(), SpecificListTasks.class);
            doneIntent.putExtra("ProjectId", project.getObjectId());
            doneIntent.putExtra("TaskType", "Done");
            startActivity(doneIntent);
        }
        else if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(loginIntent);
        }
        else if (item.getItemId() == R.id.home) {
            Intent homeIntent = new Intent(getApplicationContext(), Home.class);
            startActivity(homeIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
