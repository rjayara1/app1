package com.example.rajesh.app1;




import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.*;

import objs.User;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ID:";
    private EditText usernameField,passwordField;
    private TextView status,role,method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        usernameField = (EditText)findViewById(R.id.editText1);
        passwordField = (EditText)findViewById(R.id.editText2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

     @Override
   public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();


        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        User user = dbHandler.validate(username, password);

        if (user != null) {
            Intent intent = new Intent(this, Dashboard.class);
            ((myApp) this.getApplication()).setUSER_ID(user.getID()); //set USER_ID
            startActivity(intent);

        } else {

            String message = "Invalid Login Credentials";

        }
    }

    public void register(View view) {

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Random r = new Random();
        User U = dbHandler.findUser(username,-1);
        if (U ==null) {

        User user =
                new User(username, password, r.nextInt(90000) + 10000);
        dbHandler.addUser(user);
        dbHandler.createUserTable(user.getID());
    }
        else{
            //TODO: ERROR: CANNOT REGISTER USERNAME ALREADY EXISTS

        }
    }




}


