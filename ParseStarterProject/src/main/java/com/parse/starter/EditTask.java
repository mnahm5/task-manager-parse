package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

public class EditTask extends AppCompatActivity {

    private ParseObject task = null;
    private EditText etTaskTitle;
    private EditText etDescription;
    private Spinner spTaskType;
    private DatePicker dpDueDate;
    private TimePicker tpDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        String taskId = getIntent().getExtras().getString("taskId");
        getTaskDetails(taskId);
        etTaskTitle = (EditText) findViewById(R.id.etTaskTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        spTaskType = (Spinner) findViewById(R.id.spTaskType);
        dpDueDate = (DatePicker) findViewById(R.id.dpDueDate);
        tpDueDate = (TimePicker) findViewById(R.id.tpDueDate);

        toggleEnabled(false);
    }

    public void getTaskDetails(final String taskId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
        query.getInBackground(taskId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (object != null) {
                        task = object;
                        setDetails();
                    }
                }
            }
        });
    }

    public void setDetails() {
        setTitle(task.get("title").toString());
        
    }

    public void toggleEnabled(boolean condition) {
        etTaskTitle.setEnabled(condition);
        etDescription.setEnabled(condition);
        spTaskType.setEnabled(condition);
        dpDueDate.setEnabled(condition);
        tpDueDate.setEnabled(condition);
    }
}
