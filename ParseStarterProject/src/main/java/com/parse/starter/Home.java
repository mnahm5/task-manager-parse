package com.parse.starter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements TaskFeed.OnFragmentInteractionListener {

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        ArrayList<String> projectIds = getProjectIds();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.home_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.projects) {
            Intent projectsIntent = new Intent(getApplicationContext(), Projects.class);
            startActivity(projectsIntent);
        }
        else if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(loginIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> getProjectIds()
    {
        final ArrayList<String> projectIds = new ArrayList<String>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Projects");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject project: objects) {
                            String projectId = project.getObjectId();
                            projectIds.add(projectId);
                        }
                        Log.i("Info", "Projects Added");
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("userProjectIds", projectIds);
                        TaskFeed taskFeed = new TaskFeed();
                        taskFeed.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.taskFeed, taskFeed);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });

        return projectIds;
    }
}
