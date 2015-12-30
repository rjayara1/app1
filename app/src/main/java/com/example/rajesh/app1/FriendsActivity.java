package com.example.rajesh.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import objs.Group;
import objs.User;

public class FriendsActivity extends AppCompatActivity {
   private ListView FriendsListView;
    ArrayList<String> Friends;
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refreshFriends();
    }


    public void refreshFriends(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 2);
        int UserID = ((myApp) this.getApplication()).getUSER_ID();
        User U = dbHandler.findUser("", UserID);
        FriendsListView = (ListView) findViewById(R.id.FriendsListView);
        String[] friendArray = U.getFriends().split(":");
        Friends = new ArrayList<String>();
        MyDBHandler mDB = new MyDBHandler(this,null,null,2);
        int numFriends = friendArray.length;
        for (int i = 0;i<numFriends; i++){
            if (friendArray[i].length() > 2) {
                User V = mDB.findUser("",Integer.parseInt(friendArray[i]));
                if (V!=null) {
                    Friends.add(V.getUsername() + ", " + V.getID());
                }
            }
        }
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Friends);
        FriendsListView.setAdapter(listAdapter);

    }

    public void goToAddFriends(View v){

        Intent intent = new Intent(this,AddFriends.class);
        startActivity(intent);
    }

    public void goToFriendRequests(View v){

        Intent intent = new Intent(this,FriendRequests.class);
        startActivity(intent);
    }


}
