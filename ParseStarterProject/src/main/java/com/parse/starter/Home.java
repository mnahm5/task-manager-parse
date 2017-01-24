package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText(ParseUser.getCurrentUser().get("name").toString());
    }
}
