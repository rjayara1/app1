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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import objs.Event;
import objs.Location;
import objs.User;

public class NewEvent extends AppCompatActivity {
    Button mButton;
    EditText mEdit;
    Spinner mSpinner;
    TimePicker mTime;
    DatePicker mDate;
    //public int USER_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        mDate = (DatePicker) findViewById(R.id.datePicker);
        mDate.setCalendarViewShown(false);
        mTime = (TimePicker) findViewById(R.id.timePicker);
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int min=cal.get(Calendar.MINUTE);

        mDate.updateDate(year, month, day);
        mTime.setCurrentHour(hour);
        mTime.setCurrentMinute(min);

    }

    public void sendEventTo(View v){

        Intent intent = new Intent(this, Dashboard.class);

        String DESCRIPTION = mButton.getText().toString();

        mSpinner = (Spinner) findViewById(R.id.spinner1);
        mSpinner.clearFocus();
        String LOC = mSpinner.getSelectedItem().toString();
        mTime = (TimePicker) findViewById(R.id.timePicker);
        int month =  mDate.getMonth();
        int day = mDate.getDayOfMonth();
        String MMDD = month + "/" + day;
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
        String time =zeros1 + "" + hour + ":" + zeros2 + "" + min + " " + AMPM + " " + MMDD;

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);

        Location loc = new Location(LOC,DESCRIPTION);
        Random r = new Random();

        Event e  =
                new Event(loc, DESCRIPTION, time, r.nextInt(100000), ((myApp) this.getApplication()).getUSER_ID());

        dbHandler.addEvent(e);
        dbHandler.addEventToUser(e, ((myApp) this.getApplication()).getUSER_ID());

                startActivity(intent);

    }


}



