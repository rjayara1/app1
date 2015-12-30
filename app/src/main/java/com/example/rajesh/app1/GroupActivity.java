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
import android.widget.LinearLayout;

import objs.Event;
import objs.Group;

public class GroupActivity extends AppCompatActivity {
    public static final String GROUPID_MESSAGE = "GROUP_ID";
    public static final String GROUP_DESCRIPTION = "GROUP_DESCRIPTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button goHome = (Button) findViewById(R.id.GoHome);

        refreshGroups();

    }

    public void refreshGroups(){


        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);

        //TODO: find a more efficent way of getting these groups

        Group[] groups = dbHandler.getGroupsWithUser(((myApp) this.getApplication()).getUSER_ID());
        int numGroups = groups.length;
        for (int i = 0;i<numGroups; i++){
            if (groups[i] != null) {
                Button b = new Button(this);

                String buttonText = groups[i].getName() + "\n" + "Members: ";
                String[] members = groups[i].getMembers().split(":");
                for (int j = 0; j<members.length; j++){
                    if (j != members.length -1) {
                        buttonText = buttonText + members[j] + ", ";
                    }
                    else{
                        buttonText = buttonText + members[j];
                    }
                }
                final String message = buttonText;
                final int GroupID = groups[i].getGroupID();
          //      Log.d("Group: ", buttonText +"");
                b.setText(buttonText);
                LinearLayout layout = (LinearLayout) findViewById(R.id.GroupLayout);
                b.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                final Activity tempAct = this;
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(tempAct, GoToGroup.class);
                        intent.putExtra(GROUPID_MESSAGE, GroupID);
                        intent.putExtra(GROUP_DESCRIPTION, message);
                        startActivity(intent);
                    }
                });

                layout.addView(b);
            }
        }
    }
/*
    public void onRestart(){
        super.onRestart();
        setContentView(R.layout.activity_group);
        refreshGroups();

    }
    public void onResume(){
        super.onResume();
        refreshGroups();
    }*/
    public void createGroup(View v){
        Intent i = new Intent(this, CreateGroupPage.class);
        startActivity(i);
    }

    public void goToDash(View v){
        Intent i = new Intent(this,Dashboard.class);
        startActivity(i);
    }
    public void cleanHouse(){

    }
}
