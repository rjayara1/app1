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
import android.widget.Toast;

import com.google.gson.Gson;

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
        MyDBHandler db = new MyDBHandler(this,null,null,2);
        Location[] locations = db.getLocationsFromUser(((myApp) this.getApplication()).getUSER_ID());
        int numLocations = locations.length;
        String[] items = new String[numLocations+3];
        items[0] = "Choose Location";
        items[1] = "Test Location";
        for (int i = 0; i<numLocations; i++){
            if (locations[i]!=null){
                Log.d("Location" + i,locations[i].getLoc() );
                items[i+2] = locations[i].getLoc() + ", " + locations[i].getID();
            }
            else{
                items[i+2] = "";
            }
        }
        items[numLocations+2] = "New Location";

       // String[] items = new String[]{"The Ratty", "The VDub", "Andrews", "Joe's"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mSpinner.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition("Choose Location");
        mSpinner.setSelection(spinnerPosition);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String mselection = mSpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "selected " + mselection, 30).show();
                /**** do your code*****/

                if (mselection.equals("New Location")){
                    Intent i = new Intent(getApplicationContext(), NewLocation.class);
                    startActivity(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });






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

        Intent intent = new Intent(this, SendEventTo.class);

        String[] FULLDESCRIPTION = mButton.getText().toString().split(",");
        //TODO: splitting at comma to leave out ID, change to something better


        String DESCRIPTION = FULLDESCRIPTION[0];

        mSpinner = (Spinner) findViewById(R.id.spinner1);
        mSpinner.clearFocus();
        String LOC = mSpinner.getSelectedItem().toString();
        if (!LOC.equals("Choose Location")) {
            mTime = (TimePicker) findViewById(R.id.timePicker);
            int month = mDate.getMonth();
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
            } else {
                AMPM = "AM";
                if (hour == 0) {
                    hour += 12;
                }
            }
            String zeros1 = "";
            String zeros2 = "";
            if (hour < 10) {
                zeros1 = "0";
            }
            if (min < 10) {
                zeros2 = "0";
            }
            String time = zeros1 + "" + hour + ":" + zeros2 + "" + min + " " + AMPM + " " + MMDD;

            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);

            Location loc = new Location(LOC, DESCRIPTION);
            Random r = new Random();

            Event e =
                    new Event(loc, DESCRIPTION, time, r.nextInt(100000), ((myApp) this.getApplication()).getUSER_ID());

            //dbHandler.addEvent(e);
            dbHandler.addEventToUser(e, ((myApp) this.getApplication()).getUSER_ID());
            intent.putExtra("Event", new Gson().toJson(e));


            startActivity(intent);
        }

    }



    }



