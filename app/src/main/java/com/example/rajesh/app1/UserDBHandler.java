package com.example.rajesh.app1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

import objs.Event;
import objs.Group;
import objs.Location;
import objs.User;


public class UserDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3; //TODO: figure out DB versions
    private static final String DATABASE_NAME = "AppDB.db"; //All tables are in the same DB

    //Info for Login Database
    private static final String TABLE_CREDENTIALS = "Credentials";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "_username";
    public static final String COLUMN_PASSWORD = "_password";

    //Info for Individual Group Event Tables
    private static final String TABLE_USER_EVENTS = "UserEvents";
    public static final String COLUMN_EVENTID = "_id";
    public static final String COLUMN_LOCATION = "_location";
    public static final String COLUMN_TIME = "_time";
    public static final String COLUMN_OWNER = "_owner";




    public UserDBHandler(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_CREDENTIALS_TABLE = "CREATE TABLE " +
                TABLE_CREDENTIALS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME
                + " TEXT," + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_CREDENTIALS_TABLE);

        User[] users = this.getUsers();


        for (int i = 0; i <users.length; i++){
            if (users[i]!= null) {
                String CREATE_USER_EVENTS_TABLE = "CREATE TABLE " +
                        TABLE_USER_EVENTS  + users[i].getID() + "("
                        + COLUMN_EVENTID + " INTEGER PRIMARY KEY," + COLUMN_LOCATION
                        + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_OWNER + " Integer" + ")";
                db.execSQL(CREATE_USER_EVENTS_TABLE);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);
        onCreate(db);
    }


    public User validate(String Username,String Password) {

        String query = "Select * FROM " + TABLE_CREDENTIALS + " WHERE " + COLUMN_USERNAME +
                " =  \"" + Username + "\"" + " AND " + COLUMN_PASSWORD + " = \"" + Password + "\"";
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

    public void addUser(User U) {
        ContentValues values = new ContentValues();
        Random randomGenerator = new Random();
        int randID = randomGenerator.nextInt(10000);
        values.put(COLUMN_ID, randID);
        values.put(COLUMN_USERNAME, U.getUsername());
        values.put(COLUMN_PASSWORD, U.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CREDENTIALS, null, values);
        db.close();
    }

    public User findUser(String name, int ID) {
        String query = "Select * FROM " + TABLE_CREDENTIALS + " WHERE " + COLUMN_ID + " =  \"" + ID
                + "\"" + " OR " + COLUMN_USERNAME + " =  \"" +  name + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User U = new User();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            U.setID(Integer.parseInt(cursor.getString(0)));
            U.setUsername(cursor.getString(1));
            U.setPassword("");
            cursor.close();
        } else {
            U = null;
        }
        db.close();
        return U;
    }
    public User[] getUsers(){
        String query = "Select * FROM " + TABLE_CREDENTIALS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        User[] users = new User[Cursize];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            users[i] = new User();
            users[i].setID(Integer.parseInt(cursor.getString(0)));
            users[i].setUsername(cursor.getString(1));
            users[i].setPassword(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return users;
    }

    public boolean removeUser(User U){
        boolean result = false;
        String query = "Select * FROM " + TABLE_CREDENTIALS + " WHERE " + COLUMN_USERNAME +
                " =  \"" + U.getUsername() + "\"" + " OR " + COLUMN_ID + " =  \"" +  U.getID() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_CREDENTIALS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(Integer.parseInt(cursor.getString(0)))});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


    //BEGIN INDIVIDUAL USER EVENTS

    public void addEventToUser(Event e, int UserID) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENTID, e.getID());
        values.put(COLUMN_LOCATION, e.getLocation().toString());
        values.put(COLUMN_TIME, e.getTime());
        values.put(COLUMN_OWNER, e.getOwnerID());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USER_EVENTS + UserID, null, values);
        db.close();
    }
    public Event findEventInGroup(String location, int ID, int UserID) {
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_EVENTID + " =  \"" + ID
                + "\"" + " OR " + COLUMN_LOCATION + " =  \"" +  location + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Event e = new Event();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            e.setID(Integer.parseInt(cursor.getString(0)));
            e.setLocation(new Location(cursor.getString(1)));
            e.setTime(cursor.getString(2));
            e.setOwnerID(Integer.parseInt(cursor.getString(3)));
            cursor.close();
        } else {
            e = null;
        }
        db.close();
        return e;
    }
    public int getNumEventsInGroup(int UserID){
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        cursor.close();
        db.close();
        return Cursize;
    }
    public Event[] getEventsFromGroup(int UserID){
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        Event[] events = new Event[Cursize];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            events[i] = new Event();
            events[i].setID(Integer.parseInt(cursor.getString(0)));
            events[i].setLocation(new Location(cursor.getString(1)));
            events[i].setTime(cursor.getString(2));
            events[i].setOwnerID(Integer.parseInt(cursor.getString(3)));
        }
        cursor.close();
        db.close();
        return events;
    }
    public boolean removeEventFromGroup(Event e, int UserID){
        boolean result = false;
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_LOCATION +
                " =  \"" + e.getLocation().toString() + "\"" + " OR " + COLUMN_EVENTID + " =  \"" +  e.getID() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_USER_EVENTS + UserID, COLUMN_EVENTID + " = ?",
                    new String[] { String.valueOf(Integer.parseInt(cursor.getString(0)))});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean removeEventsFromGroup(int UserID ){
        boolean result = false;
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        Event[] events = new Event[Cursize+1];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            db.delete(TABLE_USER_EVENTS + UserID, COLUMN_EVENTID + " = ?",
                    new String[]{String.valueOf(Integer.parseInt(cursor.getString(0)))});
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }




}
