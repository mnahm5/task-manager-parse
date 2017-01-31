package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SpecificListTasks extends AppCompatActivity {

    private ParseObject project;
    private String taskType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_list_tasks);

        Intent intent = getIntent();
        String projectId = intent.getExtras().getString("ProjectId");
        taskType = intent.getExtras().getString("TaskType");
        getProjectDetails(projectId);

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
