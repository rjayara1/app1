package com.example.rajesh.app1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import objs.User;

public class AddFriends extends AppCompatActivity {
    private Button UserButton;
    private Button IDButton;
    private EditText EditTextUser;
    private EditText EditTextID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EditTextID = (EditText) findViewById(R.id.ADD_FRIEND_BY_ID_EDIT_TEXT);
        EditTextUser = (EditText) findViewById(R.id.ADD_FRIEND_BY_USERNAME_EDIT_TEXT);
        UserButton = (Button) findViewById(R.id.Add_By_Username);
        IDButton = (Button) findViewById(R.id.Add_By_ID);
    }

    public void addFriendByID(View v){
        EditTextID = (EditText) findViewById(R.id.ADD_FRIEND_BY_ID_EDIT_TEXT);
        int ID = Integer.parseInt(EditTextID.getText().toString());
        MyDBHandler mDB = new MyDBHandler(this,null,null,2);
        User U = mDB.findUser("",ID);

        String  str = U.getFriendRequests();
        //ADDS A NEW FRIEND REUQEST TO USER U'S FRIEND REQUEST LIST
        //TO BE APPROVED BY USER U, AND THEN ADDED TO BOTH U'S AND CALLING USER'S
        //RESPECTIVE FRIEND LISTS

        mDB.addFriendRequestToUser(((myApp) this.getApplication()).getUSER_ID(), U.getID());
        //TODO: FINISH THIS METHOD -- NEED TO FINISH METHODS IN MYDB FIRST

    }

    public void addFriendByUsername(View v){
        EditTextUser= (EditText) findViewById(R.id.ADD_FRIEND_BY_USERNAME_EDIT_TEXT);
        String Username = EditTextID.getText().toString();
        MyDBHandler mDB = new MyDBHandler(this,null,null,2);
        User U = mDB.findUser(Username,-1);

        String  str = U.getFriendRequests();
        //ADDS A NEW FRIEND REUQEST TO USER U'S FRIEND REQUEST LIST
        //TO BE APPROVED BY USER U, AND THEN ADDED TO BOTH U'S AND CALLING USER'S
        //RESPECTIVE FRIEND LISTS

        mDB.addFriendRequestToUser(((myApp) this.getApplication()).getUSER_ID(), U.getID());
        //TODO: FINISH THIS METHOD -- NEED TO FINISH METHODS IN MYDB FIRST

    }

}
