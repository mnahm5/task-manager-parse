package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class AddTask extends AppCompatActivity {

    private String projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setTitle("Add Task");

        projectId = getIntent().getExtras().getString("ProjectId");

        Toast.makeText(getApplicationContext(), projectId, Toast.LENGTH_SHORT).show();
    }

    public void add_task(View view) {
        final EditText etTaskTitle = (EditText) findViewById(R.id.etTaskTitle);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final Spinner spTaskType = (Spinner) findViewById(R.id.spTaskType);
        final DatePicker dpDueDate = (DatePicker) findViewById(R.id.dpDueDate);
        final TimePicker tpDueDate = (TimePicker) findViewById(R.id.tpDueDate);

        if (spTaskType.getSelectedItem().toString().equals("Choose Task List")) {
            Toast.makeText(
                    getApplicationContext(),
                    "Need to select which List to put current task in\nTry Again",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Date dueDate = new Date(
                    dpDueDate.getYear(),
                    dpDueDate.getMonth(),
                    dpDueDate.getDayOfMonth(),
                    tpDueDate.getCurrentHour(),
                    tpDueDate.getCurrentMinute()
            );
            ParseObject task = new ParseObject("Tasks");
            task.put("projectId", projectId);
            task.put("title", etTaskTitle.getText().toString());
            task.put("description", etDescription.getText().toString());
            task.put("type", spTaskType.getSelectedItem().toString());
            task.put("dueDate", dueDate);
            task.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Task Saved",
                                Toast.LENGTH_SHORT
                        ).show();
                        finish();
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
}
