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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewEvent extends AppCompatActivity {
    Button mButton;
    EditText mEdit;
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSpinner = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"The Ratty", "The VDub", "Andrews", "Joe's"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mSpinner.setAdapter(adapter);


        mButton = (Button)findViewById(R.id.createButton);
        mEdit   = (EditText)findViewById(R.id.editTextDescrip);

    }

    public void create(View v){

        Intent intent = new Intent(this, Dashboard.class);
        String DESCRIPTION = mButton.getText().toString();

        //TODO: get input selection from drop down Spinner and time widget

        //mSpinner.setOnItemSelectedListener(this);
        startActivity(intent);


    }


}



