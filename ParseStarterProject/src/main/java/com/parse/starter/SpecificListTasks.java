package com.parse.starter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class SpecificListTasks extends AppCompatActivity implements TaskFeed.OnFragmentInteractionListener {

    private ParseObject project;
    private String taskType;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_list_tasks);

        Intent intent = getIntent();
        String projectId = intent.getExtras().getString("ProjectId");
        taskType = intent.getExtras().getString("TaskType");
        getProjectDetails(projectId);

        Bundle bundle = new Bundle();

        ArrayList<String> projectIds = new ArrayList<String>();
        projectIds.add(projectId);
        bundle.putStringArrayList("userProjectIds", projectIds);

        ArrayList<String> taskTypes = new ArrayList<String>();
        taskTypes.add(taskType);
        bundle.putStringArrayList("taskTypes", taskTypes);

        TaskFeed taskFeed = new TaskFeed();
        taskFeed.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.taskFeed, taskFeed);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void getProjectDetails(String projetctId)
    {
        String projectId = getIntent().getExtras().getString("ProjectId");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Projects");
        query.getInBackground(projectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null && object != null) {
                    project = object;
                    setTitle(project.getString("name") + " - " + taskType);
                }
            }
        });
    }
}
