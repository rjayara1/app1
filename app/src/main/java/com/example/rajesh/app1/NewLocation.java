package com.example.rajesh.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

import objs.Location;
import objs.User;

public class NewLocation extends AppCompatActivity {
    EditText mEdit;
    EditText mEditDes;
    int UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);
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
        UserID= ((myApp) this.getApplication()).getUSER_ID();
        mEdit = (EditText) findViewById(R.id.locationNameText);
        mEditDes = (EditText) findViewById(R.id.locationDescriptionText);
    }



     public void createLocation(View v){
         UserID= ((myApp) this.getApplication()).getUSER_ID();
         mEditDes = (EditText) findViewById(R.id.locationDescriptionText);
         mEdit = (EditText) findViewById(R.id.locationNameText);
         String loc = mEdit.getText().toString();
         String des = mEditDes.getText().toString();

         MyDBHandler db = new MyDBHandler(this,null,null,2);
         Random R = new Random();
         Location L = new Location(R.nextInt(90000)+10000, loc, des);
         Log.d("LocationID is !!!!!: ", L.getID() + "");
         db.addLocationToUser(L, UserID);
         db.close();
         Intent I = new Intent(this,NewEvent.class);
         startActivity(I);

     }

}
