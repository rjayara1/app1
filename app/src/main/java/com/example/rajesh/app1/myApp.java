package com.example.rajesh.app1;

/**
 * Created by Rajesh on 12/24/2015.
 */

import android.app.Application;

public class myApp extends Application {

    private int USER_ID;

    //TO SET: ((myApp) this.getApplication()).setUSER_ID(NEW_ID);
    public int getUSER_ID() {
        return USER_ID;
    }

    //TO GET: int ID = ((myApp) this.getApplication()).getUSER_ID();
    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }
}