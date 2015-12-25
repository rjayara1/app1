package com.example.rajesh.app1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import objs.Event;
import objs.Location;
import objs.User;

public class NewEvent extends AppCompatActivity {
    Button mButton;
    EditText mEdit;
    Spinner mSpinner;
    TimePicker mTime;
    //public int USER_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            //USER_ID  = Integer.parseInt(message);


        mSpinner = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"The Ratty", "The VDub", "Andrews", "Joe's"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mSpinner.setAdapter(adapter);
        mButton = (Button)findViewById(R.id.createButton);
        mEdit   = (EditText)findViewById(R.id.editTextDescrip);

    }

    public void create(View v){

        Intent intent = new Intent(this, Dashboard.class);

        String DESCRIPTION = mButton.getText().toString();

        mSpinner = (Spinner) findViewById(R.id.spinner1);
        mSpinner.clearFocus();
        String LOC = mSpinner.getSelectedItem().toString();
        mTime = (TimePicker) findViewById(R.id.timePicker);


        int hour = mTime.getCurrentHour();
        int min = mTime.getCurrentMinute();
        String AMPM = "";

        if (hour >= 12) {
            AMPM = "PM";
            if (hour > 12) {
                hour = hour - 12;
            }
        }
        else {
            AMPM = "AM";
            if (hour == 0) {
                hour += 12;
            }
        }
        String zeros1 = "";
        String zeros2 = "";
        if (hour<10){
            zeros1 = "0";
        }
        if (min<10){
            zeros2 = "0";
        }
        String time = zeros1 + "" + hour + ":" + zeros2 + "" +min + " " + AMPM;

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);

        Location loc = new Location(LOC,DESCRIPTION);
        Event e  =
                new Event(loc, DESCRIPTION, time, (int)(Math.random()*10000.0), ((myApp) this.getApplication()).getUSER_ID());

        dbHandler.addEvent(e);

        startActivity(intent);


    }


}



