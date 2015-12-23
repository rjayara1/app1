package com.example.rajesh.app1;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import objs.User;

/**
 * Created by Rajesh on 12/22/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Credentials.db";
    private static final String TABLE_CREDENTIALS = "Credentials";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "_username";
    public static final String COLUMN_PASSWORD = "_password";

    private int numUsers;

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        numUsers = 0;
    }


    public int getSize() {
        return numUsers;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CREDENTIALS_TABLE = "CREATE TABLE " +
                TABLE_CREDENTIALS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME
                + " TEXT," + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_CREDENTIALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);
        onCreate(db);
    }

    public void addUser(User U) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,U.getID());
        values.put(COLUMN_USERNAME, U.getUsername());
        values.put(COLUMN_PASSWORD, U.getPassword());


        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CREDENTIALS, null, values);
        db.close();
        numUsers++;
    }

    public User validate(String Username,String Password) {
        String query = "Select * FROM " + TABLE_CREDENTIALS + " WHERE " + COLUMN_USERNAME + " =  \"" + Username + "\"" + " AND " + COLUMN_PASSWORD + " = \"" + Password + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        User user = new User();


        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            user.setID(Integer.parseInt(cursor.getString(0)));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            cursor.close();
        } else {
            user = null;
        }
        db.close();
        return user;

    }


}
