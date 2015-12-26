package com.example.rajesh.app1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventGoTo extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_event_go_to);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            TextView textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText(message);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.GoToEventLayout);
            layout.addView(textView);

    }


    public void onStop() {
        super.onStop();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void onBackPressed(){
        super.onBackPressed();
        cleanHouse();
        System.exit(0);
    }

    public void cleanHouse(){

    }

}
