package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Projects extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        setTitle("Projects");

        final ListView lvProjects = (ListView) findViewById(R.id.lvProjects);
        final List<String> projectNames = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Projects");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject project: objects) {
                            projectNames.add(project.get("name").toString());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                Projects.this,
                                android.R.layout.simple_list_item_1,
                                android.R.id.text1,
                                projectNames);

                        lvProjects.setAdapter(adapter);

                        lvProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplicationContext(), Tasks.class);
                                intent.putExtra("ProjectId", objects.get(position).getObjectId());
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.projects_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_project) {
            Intent projectsIntent = new Intent(getApplicationContext(), AddProject.class);
            startActivity(projectsIntent);
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
