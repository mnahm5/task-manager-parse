package com.parse.starter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskFeed extends Fragment {
    private List<ParseObject> tasks = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    private ListView lvTasks;

    public TaskFeed() {

    }

    public static TaskFeed newInstance() {
        return new TaskFeed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_feed, container, false);

        ArrayList<String> projectIds = null;
        ArrayList<String> taskTypes = null;
        if (getArguments() != null) {
            projectIds = getArguments().getStringArrayList("userProjectIds");
            taskTypes = getArguments().getStringArrayList("taskTypes");
        }

        if (taskTypes == null) {
            taskTypes = new ArrayList<String>();
            taskTypes.add("To Do");
            taskTypes.add("Doing");
        }

        lvTasks = (ListView) view.findViewById(R.id.lvTasks);

        if (projectIds != null && projectIds.size() > 0) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
            query.whereContainedIn("projectId", projectIds);
            query.whereContainedIn("type", taskTypes);
            query.orderByAscending("dueDate");
            query.setLimit(8);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            List<Map<String, String>> taskData = new ArrayList<Map<String, String>>();
                            tasks = objects;
                            for (ParseObject task: objects) {
                                Map<String, String> taskInfo = new HashMap<String, String>();
                                taskInfo.put("title", task.getString("title"));
                                taskInfo.put("dueDate", task.getDate("dueDate").toString());
                                taskData.add(taskInfo);
                            }
                            SimpleAdapter simpleAdapter = new SimpleAdapter(
                                    getContext(),
                                    taskData,
                                    android.R.layout.simple_expandable_list_item_2,
                                    new String[] {"title", "dueDate"},
                                    new int[] {android.R.id.text1, android.R.id.text2});

                            lvTasks.setAdapter(simpleAdapter);
                            Log.i("Info", "Tasks Found");
                        }
                        else {
                            Log.i("Info", "No Tasks Found");
                        }
                    }
                    else {
                        Log.i("Info", "Error Occurred");
                    }
                }
            });

            lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), EditTask.class);
                    intent.putExtra("taskId", tasks.get(position).getObjectId());
                    startActivity(intent);
                }
            });


        }
        else {
            Log.i("Error", "Projects not found");
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
