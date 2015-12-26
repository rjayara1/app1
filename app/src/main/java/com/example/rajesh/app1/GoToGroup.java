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
import android.widget.RelativeLayout;
import android.widget.TextView;

import objs.User;

public class GoToGroup extends AppCompatActivity {
    private Button mAddUserID;
    private int GroupID;
    private EditText mTextUserID;
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
        layout.addView(textView);

        mAddUserID = (Button) findViewById(R.id.Add_Member);

        mTextUserID = (EditText) findViewById(R.id.TextUserID);
    }



    public void addNewGroupMembers(View v){
        Intent i = new Intent(this,GroupActivity.class);
        int ID = Integer.parseInt(mTextUserID.getText().toString());
        MyDBHandler mDB = new MyDBHandler(this, null, null, 1);
        User U = mDB.findUser("",ID);
        if (U!=null) {
            mDB.addUserToGroup(ID, GroupID);
            startActivity(i);
        }
        else {
                //TODO: ACTION on trying to add a non-existant User to a Group.

            startActivity(i);
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        cleanHouse();
        System.exit(0);
    }
    public void cleanHouse(){

    }
}
