package com.example.rajesh.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

import objs.Group;

public class CreateGroupPage extends AppCompatActivity {
    EditText GroupName;
    EditText GroupDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GroupName = (EditText) findViewById(R.id.CreateGroupName);
        GroupDescription= (EditText) findViewById(R.id.CreateGroupDescription);
    }

    public void makeGroup(View v){
        String name = GroupName.getText().toString();
        String Description = GroupDescription.getText().toString();
        Random r = new Random();
        Group g = new Group(name,Description,r.nextInt(100000), ((myApp) this.getApplication()).getUSER_ID() + ":");
        MyDBHandler db = new MyDBHandler(this,null,null,2);
        db.addGroup(g);
        //db.createGroupTable(g.getGroupID());
        db.close();
        Intent intent = new Intent(this, GroupActivity.class);
        startActivity(intent);

    }

}
