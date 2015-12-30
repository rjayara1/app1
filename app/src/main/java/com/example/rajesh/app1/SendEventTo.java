package com.example.rajesh.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import objs.Event;
import objs.Group;
import objs.User;

public class SendEventTo extends AppCompatActivity {
    int UserID;
    private String SEND_TO_GROUP_IDS;
    private String SEND_TO_USER_IDS;
    Event theEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_event_to);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String jsonEvent;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFriendsGroups();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonEvent = extras.getString("Event");
        theEvent = new Gson().fromJson(jsonEvent, Event.class);
    }
    }

    private void getFriendsGroups(){
        SEND_TO_GROUP_IDS = "";
        SEND_TO_USER_IDS = "";
        UserID = ((myApp) this.getApplication()).getUSER_ID();
        MyDBHandler mDB = new MyDBHandler(this, null, null, 2);
        User U = mDB.findUser("", UserID);
        String[] friends = U.getFriends().split(":");
        int numFriends = friends.length;  //maybe needs to be -1

        Group[] groups = mDB.getGroupsWithUser(UserID);
        int numGroups = groups.length;
        String message = "Groups:";
        TextView GroupText  = new TextView(this);
        GroupText.setText(message);
        GroupText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout layout = (LinearLayout) findViewById(R.id.SendEventLayout);
        layout.addView(GroupText);

        for (int i = 0; i<numGroups; i++){
            if (groups[i]!= null){
                Button b = new Button(this);
                final int GroupID = groups[i].getGroupID();
                final String buttonText = groups[i].getName() + ", " + GroupID;

                b.setText(buttonText);
                b.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                final Activity tempAct = this;
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //TODO: LIGHT UP ON CLICK, AND SELECT GROUP TO SEND EVENT TO
                        if (SEND_TO_GROUP_IDS.indexOf(GroupID + ":") == -1) {
                            //Then this group has not been added yet
                            SEND_TO_GROUP_IDS += GroupID + ":";
                            //lightup!
                        } else{
                            //Then it has been added yet, so the reclick means remove it
                            SEND_TO_GROUP_IDS = SEND_TO_GROUP_IDS.replace(GroupID + ":", "");
                            //lightDown

                        }

                    }
                });

                layout.addView(b);
            }
        }
        String message2 = "Friends:";
        TextView FriendText  = new TextView(this);
        GroupText.setText(message);
        GroupText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(FriendText);

        for (int i = 0; i<numFriends; i++){
            if (friends[i].length() > 2 ){
                Button b = new Button(this);
                U = mDB.findUser("",Integer.parseInt(friends[i])); //TODO: THESE CALLS NEED DATABASE EXCEPTION HANDLING
                if (U !=null){
                    final String buttonText = U.getUsername();
                    final int FriendID = Integer.parseInt(friends[i]);
                    b.setText(buttonText);
                    b.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
                    final Activity tempAct = this;
                    b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //TODO: LIGHT UP ON CLICK, AND SELECT GROUP TO SEND EVENT TO
                        if (SEND_TO_USER_IDS.indexOf(FriendID + ":") == -1) {
                            //Then this group has not been added yet
                            SEND_TO_USER_IDS += FriendID + ":";
                            //lightup!
                        } else{
                            //Then it has been added yet, so the reclick means remove it
                            SEND_TO_USER_IDS = SEND_TO_USER_IDS.replace(FriendID + ":", "");
                            //lightDown

                        }

                    }
                });

                layout.addView(b);
                } else{

                    //USER DOES NOT EXIST
                }
            }
        }




    }

    public void sendOutEvents(View v) {
        //TODO: HERE WE SEND THE STRINGS SEND_TO_USER AND SEND_TO_GROUPS BACK TO NEWEVENT.CLASS
        //TODO: THEN WE TAKE THEM, AND SEND THE EVENT E TO THEM, AND TAKE US ALL THE WAY BACK OUT TO DASHBOARD.CLASS
        if (theEvent != null) {
            MyDBHandler mDB = new MyDBHandler(this, null, null, 2);
            String[] users = SEND_TO_USER_IDS.split(":");
            String[] groups = SEND_TO_GROUP_IDS.split(":");

            for (String user : users) {
                if (user.length() > 2) {
                    mDB.addEventToUser(theEvent, Integer.parseInt(user));
                }
            }

            for (String g : groups) {
                if (g.length() > 2) {
                    String[] Members = mDB.getMembersFromGroup(Integer.parseInt(g)).split(":");
                    for (String s : Members) {
                        if (s.length() > 2) {
                            mDB.addEventToUser(theEvent, Integer.parseInt(s));
                        }
                    }
                }
            }

        }
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);

    }


}
