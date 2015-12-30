package com.example.rajesh.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import objs.Group;
import objs.User;

public class GoToGroup extends AppCompatActivity {
    ArrayList<String> Members;
    private Button mAddUserID;
    private int GroupID;
    private EditText mTextUserID;
    private ArrayAdapter<String> listAdapter;
    private ListView mainListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra(GroupActivity.GROUP_DESCRIPTION);
        GroupID = intent.getIntExtra(GroupActivity.GROUPID_MESSAGE, -1);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.GoToGroupLayout);
        //layout.addView(textView);

        mAddUserID = (Button) findViewById(R.id.Add_Member);

        mTextUserID = (EditText) findViewById(R.id.TextUserID);

        refreshUsers();
    }

    public void refreshUsers(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);
        Group G = dbHandler.findGroup(GroupID);
        mainListView = (ListView) findViewById(R.id.RequestslistView);
        String[] members = G.getMembers().split(":");
        Members = new ArrayList<String>();

        int numMembers = members.length;
        for (int i = 0;i<numMembers; i++){
            if (members[i].length() > 2) {

                Members.add(members[i]);
            }
        }
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Members);
        mainListView.setAdapter(listAdapter);

    }



    public void addNewGroupMembers(View v){
        Intent i = new Intent(this,GroupActivity.class);
        int ID = Integer.parseInt(mTextUserID.getText().toString());
        MyDBHandler mDB = new MyDBHandler(this, null, null, 1);
        User U = mDB.findUser("",ID);
        if (U!=null) {
            mDB.addUserToGroup(ID, GroupID);
            mDB.close();
            mTextUserID.setText("");
            startActivity(i);
        }
        else {
                //TODO: ACTION on trying to add a non-existant User to a Group.

            startActivity(i);
        }
    }

    public void leaveGroup(View v){
        int UserID = ((myApp) this.getApplication()).getUSER_ID();
        MyDBHandler mDB = new MyDBHandler(this, null, null, 1);
        mDB.removeUserFromGroup(UserID, GroupID);
        mDB.close();
        Intent i = new Intent(this,GroupActivity.class);
        startActivity(i);
    }

    public void onBackPressed(){
        super.onBackPressed();
        cleanHouse();
        System.exit(0);
    }
    public void cleanHouse(){

    }
}
