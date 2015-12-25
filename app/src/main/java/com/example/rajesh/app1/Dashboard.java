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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

import objs.Event;
import objs.User;

public class Dashboard extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ID:";
    public final static String EVENT_MESSAGE = "Event:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
        Intent intent = getIntent();

       debugPrintUsers(); //TODO: remove, this is for viewing all registered users.
       refreshEvents();
    }

    public void refreshEvents() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);
        //TODO: change so that we only update if the number of events is greater than before



            Event[] events = dbHandler.getEvents();
            int numEvents = events.length;
        //TODO: figure out a way of doing this without sorting every time. Sorting every time is highly inefficient
             Arrays.sort(events);

            for (int i = 0;i<numEvents; i++){
                if (events[i] != null) {
                    Button b = new Button(this);
                    if (events[i].getLocation() != null && events[i].getTime() != null) {

                        final String buttonText = events[i].getOwnerID() + " will be at " + events[i].getLocation().toString() + " at " + events[i].getTime() ;
                        //final String buttonText =  ((myApp) this.getApplication()).getUSER_ID() + " will be at " + events[i].getLocation().toString() + " at " + events[i].getTime();
                        b.setText(buttonText);
                        LinearLayout layout = (LinearLayout) findViewById(R.id.DashboardLayout);
                        b.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        final Activity tempAct = this;
                        b.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent intent = new Intent(tempAct, EventGoTo.class);
                                intent.putExtra(EXTRA_MESSAGE, buttonText);
                                startActivity(intent);
                            }
                        });
                        layout.addView(b);
                    }
                }
            }

    }

        public void clearEvents(View v){
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);
            //Use to clear the event database when needed. Eventually the online
            //database will need to be manually cleaned as events expire.
            dbHandler.removeEvents();
            onResume();
        }

    public void gotoEvent(View v){
        Intent intent = new Intent(this, EventGoTo.class);
        String message = v.getTag().toString();
        intent.putExtra(EVENT_MESSAGE, message);
        startActivity(intent);
    }

    public void createEvent(View v){
        Intent intent = new Intent(this, NewEvent.class);
        startActivityForResult(intent, 1);
    }

    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_dashboard);
        refreshEvents();
    }
    public void onRestart(){
    super.onRestart();
        setContentView(R.layout.activity_dashboard);
        refreshEvents();

    }

    public void debugPrintUsers(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);

        User[] users = dbHandler.getUsers();
        onResume();
        int numUsers = users.length;
        for (int i = 0; i<numUsers; i++){
            if (users[i]!=null){
                Log.d(i + " ID:", "" +users[i].getID());
                Log.d(i + " Username:", users[i].getUsername());
                Log.d(i + " Password:", users[i].getPassword());
            }
        }

    }

    public void gotoGroups(View v){
        Intent intent = new Intent(this, GroupActivity.class);
        startActivity(intent);
    }



}
