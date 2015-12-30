package com.example.rajesh.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import objs.User;

public class FriendRequests extends AppCompatActivity {
int UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        UserID = ((myApp) this.getApplication()).getUSER_ID();
        refresh();
    }


    private void refresh(){
        UserID = ((myApp) this.getApplication()).getUSER_ID();
        final MyDBHandler mDB = new MyDBHandler(this,null,null,2);
        User U = mDB.findUser("",UserID);
        String[] Requests = U.getFriendRequests().split(":");
        int numRequests = Requests.length; //Probably need to add -1 here


        for (int i = 0; i<numRequests; i++){
            if (Requests[i].length() > 2) {
                Button b = new Button(this);
                final User requester = mDB.findUser("", Integer.parseInt(Requests[i]));
                if (requester != null) {
                    final int requesterID = requester.getID();
                    final String buttonText = requester.getUsername() + " ID: " + requesterID;
                    final PopupWindow popUp = new PopupWindow(this);

                    b.setText(buttonText);
                    final LinearLayout layout = (LinearLayout) findViewById(R.id.FriendRequestsLayout);
                    b.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    final Activity tempAct = this;
                    b.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //CREATE ACCEPT/DENY DIALOUGE BOX
                            mDB.addFriendToUsers(UserID, requesterID);
                            mDB.removeFriendRequestFromUser(requesterID, UserID);
                            //TODO: add deny option, and confirmation message
                            v.setVisibility(View.GONE);
                            refresh();
                        }
                    });

                    layout.addView(b);
                }
                else{
                    //ERROR: NO SUCH USER EXISTS
                }
            }
            else{
                //ERROR: NO SUCH USER EXISTS

        }

        }

        mDB.close();



    }

}
