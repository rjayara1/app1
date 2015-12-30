package com.example.rajesh.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Random;

import objs.Event;
import objs.Group;
import objs.Location;
import objs.User;

/**
 * Created by Rajesh on 12/22/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper{

   // private static final
    private static final int DATABASE_VERSION = 3;//1;
    private static final String DATABASE_NAME = "AppDB.db";

    //Info for User Database
    private static final String TABLE_CREDENTIALS = "Credentials";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "_username";
    public static final String COLUMN_PASSWORD = "_password";
    public static final String COLUMN_FRIENDS = "_friends";
    public static final String COLUMN_FRIEND_REQUESTS = "_friendrequests";

    //Info for Event Database
    private static final String TABLE_EVENTS = "Events";
    public static final String COLUMN_EVENTID = "_id";
    public static final String COLUMN_LOCATION = "_location";
    public static final String COLUMN_TIME = "_time";
    public static final String COLUMN_OWNER = "_owner";


    //Info for Group Database
    private static final String TABLE_GROUPS = "Groups";
    public static final String COLUMN_GROUPID = "_id";
    public static final String COLUMN_GROUPNAME = "_name";
    public static final String COLUMN_MEMBERS = "_members";

    //Info for Individual User Event Tables
    private static final String TABLE_USER_EVENTS = "UserEvents";
    private static final String COLUMN_ISLOCATION = "_locationid";
    private static final String COLUMN_LOCATIONNAME = "_locationname";
    private static final String COLUMN_LOCATIONDESCRIPTION = "_locationdescription";
    //Info for Individual Group Event Tables
    private static final String TABLE_GROUP_EVENTS= "GroupsEvents";
    public static final String COLUMN_LOCATIONEVENTID ="_locationeventid";


    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

//public void onOpen(SQLiteDatabase db){
  //  onCreate(db);
//}

    @Override
    public void onCreate(SQLiteDatabase db) {


            String CREATE_CREDENTIALS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                    TABLE_CREDENTIALS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME
                    + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_FRIENDS
                    + " TEXT," + COLUMN_FRIEND_REQUESTS
                    + " TEXT" + ")";

            String CREATE_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_EVENTS + "("
                + COLUMN_EVENTID + " INTEGER PRIMARY KEY," + COLUMN_LOCATION
                + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_OWNER + " Integer" + ")" ;

            String CREATE_GROUPS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_GROUPS + "("
                + COLUMN_GROUPID + " INTEGER PRIMARY KEY," + COLUMN_GROUPNAME
                + " TEXT," + COLUMN_MEMBERS + " TEXT" + ")" ;

            db.execSQL(CREATE_CREDENTIALS_TABLE);
            db.execSQL(CREATE_EVENTS_TABLE);
            db.execSQL(CREATE_GROUPS_TABLE);

            Log.d("EXECUTED: ", CREATE_GROUPS_TABLE);
        /*
        User[] users = this.getUsers();
        for (int i = 0; i <users.length; i++){
            if (users[i]!= null) {
                String CREATE_USER_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                        TABLE_USER_EVENTS  + users[i].getID() + "("
                        + COLUMN_EVENTID + " INTEGER PRIMARY KEY," + COLUMN_LOCATION
                        + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_OWNER + " Integer" + ")";
                db.execSQL(CREATE_USER_EVENTS_TABLE);
            }
        }

        Group[] groups = this.getGroups();
        for (int i = 0; i <groups.length; i++){
            if (groups[i]!= null) {
                String CREATE_GROUP_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                        TABLE_GROUP_EVENTS + groups[i].getGroupID() + "("
                        + COLUMN_EVENTID + " INTEGER PRIMARY KEY," + COLUMN_LOCATION
                        + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_OWNER + " Integer" + ")";
                db.execSQL(CREATE_GROUP_EVENTS_TABLE);
            }
        }
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        User[] users = this.getUsers();
        for (int i = 0; i <users.length; i++){
            if (users[i]!= null) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_EVENTS + users[i].getID());
            }
        }
        Group[] groups = this.getGroups();
        for (int i = 0; i <groups.length; i++){
            if (groups[i]!= null) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_EVENTS + groups[i].getGroupID());
            }
        }

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);

            onCreate(db);
    }




    /*******************************************************************************************
     **************************** BEGIN USER METHODS********************************************
     *******************************************************************************************/


    public void addUser(User U) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, U.getID());
            values.put(COLUMN_USERNAME, U.getUsername());
            values.put(COLUMN_PASSWORD, U.getPassword());
            values.put(COLUMN_FRIENDS, "");
            values.put(COLUMN_FRIEND_REQUESTS, "");
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
            U.setFriends(cursor.getString(3));
            U.setFriendRequests(cursor.getString(4));
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
            users[i].setFriends(cursor.getString(3));
            users[i].setFriendRequests(cursor.getString(4));
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
            user.setFriends(cursor.getString(3));
            user.setFriendRequests(cursor.getString(4));
            cursor.close();
        } else {
            user = null;
        }
        db.close();
        return user;

    }


    /*******************************************************************************************
     **************************** BEGIN FRIEND METHODS******************************************
     *******************************************************************************************/


    public void addFriendRequestToUser(int RequestingUserID, int TargetUserID) {

            User source = findUser("",RequestingUserID);
            User target = findUser("", TargetUserID);
            String curRequests = target.getFriendRequests();
            String newRequests = curRequests + RequestingUserID + ":";
            String check = "SELECT * FROM " + TABLE_CREDENTIALS + " WHERE " + COLUMN_ID + " = \"" + TargetUserID + "\"" +
                " AND " + COLUMN_FRIEND_REQUESTS + " LIKE " + "\"%" + source.getID() + "%\"";

        SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(check, null);
        if (!c.moveToFirst()) {
            String query = "UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_FRIEND_REQUESTS + "= \"" + newRequests + "\""
                    + " WHERE " + COLUMN_ID + " = \"" + TargetUserID + "\"";

            db.execSQL(query);
        }
        c.close();
        db.close();
    }

    public void removeFriendRequestFromUser(int requestID, int TargetUserID){

        User target = findUser("", TargetUserID);
        String curRequests = target.getFriendRequests();
        String newRequests = curRequests.replace(requestID + ":", "");

        String query = "UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_FRIEND_REQUESTS + "= \"" + newRequests + "\""
                + " WHERE " + COLUMN_ID + " = \"" + TargetUserID + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void addFriendToUsers(int ID1, int ID2){
        User user1 = findUser("", ID1);
        User user2 = findUser("", ID2);
        String friends1 = user1.getFriends();
        String friends2 = user2.getFriends();
        friends1 += ID2 + ":";
        friends2 += ID1 + ":";
        SQLiteDatabase db = this.getWritableDatabase();
        String check = "SELECT * FROM " + TABLE_CREDENTIALS + " WHERE " + COLUMN_ID + " = \"" + user1.getID() + "\"" +
                " AND " + COLUMN_FRIENDS + " LIKE " + "\"%" + user2.getID() + "%\"";
        Cursor c = db.rawQuery(check, null);

        if (!c.moveToFirst()) {

            String query1 = "UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_FRIENDS + "= \"" + friends1 + "\""
                    + " WHERE " + COLUMN_ID + " = \"" + ID1 + "\"";

            String query2 = "UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_FRIENDS + "= \"" + friends2 + "\""
                    + " WHERE " + COLUMN_ID + " = \"" + ID2 + "\"";
            db.execSQL(query1);
            db.execSQL(query2);
        }
        db.close();
    }

    public void removeFriendsFromUsers(int ID1, int ID2){
        User user1 = findUser("", ID1);
        String curFriends1= user1.getFriends();
        String newFriends1 = curFriends1.replace(ID2 + ":", "");

        User user2 = findUser("", ID2);
        String curFriends2= user2.getFriends();
        String newFriends2 = curFriends2.replace(ID1 + ":", "");

        String query1 = "UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_FRIEND_REQUESTS + "= \"" + newFriends1 + "\""
                + " WHERE " + COLUMN_ID + " = \"" + ID1 + "\"";

        String query2 = "UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_FRIEND_REQUESTS + "= \"" + newFriends2 + "\""
                + " WHERE " + COLUMN_ID + " = \"" + ID2 + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query1);
        db.execSQL(query2);
        db.close();
    }






    /*******************************************************************************************
     **************************** BEGIN EVENT METHODS ******************************************
     *******************************************************************************************/
    public void addEvent(Event e) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_EVENTID, e.getID());
            values.put(COLUMN_LOCATION, e.getLocation().toString());
            values.put(COLUMN_TIME, e.getTime());
            values.put(COLUMN_OWNER, e.getOwnerID());

            SQLiteDatabase db = this.getWritableDatabase();

            db.insert(TABLE_EVENTS, null, values);
            db.close();

    }
    public Event findEvent(String location, int ID) {
        String query = "Select * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_EVENTID + " =  \"" + ID
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

    public int getNumEvents(){
        String query = "Select * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        cursor.close();
        db.close();
        return Cursize;
    }

    public Event[] getEvents(){
        String query = "Select * FROM " + TABLE_EVENTS;
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

    public boolean removeEvent(Event e){
        boolean result = false;
        String query = "Select * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_LOCATION +
                " =  \"" + e.getLocation().toString() + "\"" + " OR " + COLUMN_EVENTID + " =  \"" +  e.getID() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_EVENTS, COLUMN_EVENTID + " = ?",
                    new String[] { String.valueOf(Integer.parseInt(cursor.getString(0)))});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean removeEvents(){
        boolean result = false;
        String query = "Select * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        Event[] events = new Event[Cursize+1];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            db.delete(TABLE_EVENTS, COLUMN_EVENTID + " = ?",
                    new String[]{String.valueOf(Integer.parseInt(cursor.getString(0)))});
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }



    /*******************************************************************************************
    **************************** BEGIN INDIVIDUAL USER EVENTS **********************************
    *******************************************************************************************/

    public void createUserTable(int UserID){
        String CREATE_USER_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_USER_EVENTS  + UserID + "("
                + COLUMN_LOCATIONEVENTID + " INTEGER PRIMARY KEY," + COLUMN_LOCATION
                + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_OWNER + " Integer,"
                + COLUMN_LOCATIONNAME + " TEXT," + COLUMN_LOCATIONDESCRIPTION + " TEXT," +  COLUMN_ISLOCATION + " Integer" + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_USER_EVENTS_TABLE);
        Log.d("Executed", CREATE_USER_EVENTS_TABLE);
    }

    public void addEventToUser(Event e, int UserID) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATIONEVENTID, e.getID());
        values.put(COLUMN_LOCATION, e.getLocation().toString());
        values.put(COLUMN_TIME, e.getTime());
        values.put(COLUMN_OWNER, e.getOwnerID());
        values.put(COLUMN_ISLOCATION, -1);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USER_EVENTS + UserID, null, values);
        db.close();
    }
    public void addLocationToUser(Location L, int UserID){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATIONEVENTID, L.getID());
        values.put(COLUMN_LOCATIONNAME, L.getLoc());
        values.put(COLUMN_LOCATIONDESCRIPTION, L.getDescription());
        values.put(COLUMN_ISLOCATION, 1);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USER_EVENTS + UserID, null, values);
        db.close();
    }

    public Event findEventInUser(String location, int ID, int UserID) {
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_LOCATIONEVENTID + " =  \"" + ID
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
    public Location findLocationsInUser(String location, int UserID) {
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_ISLOCATION + " >  " + 0
                + "" + " AND " + COLUMN_LOCATIONNAME + " =  \"" +  location + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Location l = new Location();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            l.setID(Integer.parseInt(cursor.getString(0)));
            l.setLoc(cursor.getString(4));
            l.setDescription(cursor.getString(5));
            cursor.close();
        } else {
            l = null;
        }
        db.close();
        return l;
    }

    public int getNumEventsInUser(int UserID){
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_ISLOCATION + " <  " + 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        cursor.close();
        db.close();
        return Cursize;
    }
    public Event[] getEventsFromUser(int UserID){
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_ISLOCATION +
        " <  " + 0 + "";

         //NOTE, IN SOME VERSIONS OF SQL NOT EQUAL IS WRITTEN  !+ AND NOT <>

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
    public Location[] getLocationsFromUser(int UserID){
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_ISLOCATION +
                " >  " + 0 + "";

        //NOTE, IN SOME VERSIONS OF SQL NOT EQUAL IS WRITTEN  !+ AND NOT <>

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        Log.d("numLocations is !!!!!: ", Cursize + "");
        Location[] locations= new Location[Cursize];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            locations[i] = new Location(Integer.parseInt(cursor.getString(0)),cursor.getString(4),cursor.getString(5));
          //  locations[i].setID(Integer.parseInt(cursor.getString(4)));
          //  locations[i].setLoc(cursor.getString(5));
          //  locations[i].setDescription(cursor.getString(6));

        }
        cursor.close();
        db.close();
        return locations;
    }
    public boolean removeEventFromUser(Event e, int UserID){
        boolean result = false;
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_LOCATION +
                " =  \"" + e.getLocation().toString() + "\"" + " OR " + COLUMN_LOCATIONEVENTID + " =  \"" +  e.getID() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_USER_EVENTS + UserID, COLUMN_LOCATIONEVENTID + " = ?",
                    new String[] { String.valueOf(Integer.parseInt(cursor.getString(0)))});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean removeLocationFromUser(Location L, int UserID){
        boolean result = false;
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_LOCATIONNAME +
                " =  \"" + L.getLoc() + "\"" + " OR " + COLUMN_LOCATIONEVENTID + " =  \"" +  L.getID() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_USER_EVENTS + UserID, COLUMN_LOCATIONEVENTID + " = ?",
                    new String[] { String.valueOf(Integer.parseInt(cursor.getString(0)))});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean removeEventsFromUser(int UserID ){
        boolean result = false;
        String query = "Select * FROM " + TABLE_USER_EVENTS + UserID + " WHERE " + COLUMN_ISLOCATION +
                " <  " + 0;;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        Event[] events = new Event[Cursize+1];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            db.delete(TABLE_USER_EVENTS + UserID, COLUMN_LOCATIONEVENTID + " = ?",
                    new String[]{String.valueOf(Integer.parseInt(cursor.getString(0)))});
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }



    /*******************************************************************************************
     **************************** BEGIN GROUP METHODS******** **********************************
     *******************************************************************************************/


    public void addGroup(Group g) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_GROUPID, g.getGroupID());
        values.put(COLUMN_GROUPNAME, g.getName());
        values.put(COLUMN_MEMBERS, g.getMembers());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_GROUPS, null, values);
        db.close();
    }

    public boolean removeGroup(Group g){
        boolean result = false;
        String query = "Select * FROM " + TABLE_GROUPS + " WHERE " + COLUMN_GROUPNAME +
                " =  \"" + g.getName() + "\"" + " OR " + COLUMN_GROUPID + " =  \"" +  g.getGroupID() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_GROUPS, COLUMN_GROUPID + " = ?",
                    new String[] { String.valueOf(Integer.parseInt(cursor.getString(0)))});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public Group findGroup(int GroupID){
        String query = "Select * FROM " + TABLE_GROUPS + " WHERE " + COLUMN_GROUPID + " =  \"" + GroupID
                + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Group g = new Group();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            g.setGroupID(Integer.parseInt(cursor.getString(0)));
            g.setName(cursor.getString(1));
            g.setMembers(cursor.getString(2));
            cursor.close();
        } else {
            g = null;
        }
        db.close();
        return g;
    }
    public Group[] getGroups(){
        String query = "Select * FROM " + TABLE_GROUPS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        Group[] groups = new Group[Cursize];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            groups[i] = new Group();
            groups[i].setGroupID((Integer.parseInt(cursor.getString(0))));
            groups[i].setName(cursor.getString(1));
            groups[i].setMembers(cursor.getString(2));
            groups[i].setOwner(cursor.getString(3));
        }
        cursor.close();
        db.close();
        return groups;
    }
    public String getMembersFromGroup(int GroupID){
        String query = "Select * FROM " + TABLE_GROUPS + " WHERE " + COLUMN_GROUPID + " =  \"" + GroupID
                + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        String ret  = cursor.getString(2);
        db.close();
        return ret;
    }

    public Group[] getGroupsWithUser(int UserID){
        String query = "Select DISTINCT " + COLUMN_ID + "," + COLUMN_GROUPNAME + "," + COLUMN_MEMBERS +
                " FROM " + TABLE_GROUPS + " WHERE " + COLUMN_MEMBERS + " LIKE " + "\"%" + UserID + "%\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        Group[] groups = new Group[Cursize];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            groups[i] = new Group();
            groups[i].setGroupID((Integer.parseInt(cursor.getString(0))));
            groups[i].setName(cursor.getString(1));
            Log.d("Group: ", groups[i].getName());
            Log.d("ID: ", groups[i].getGroupID() + "");
            groups[i].setMembers(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return groups;
    }

    /*******************************************************************************************
     ***********************BEGIN INDIVIDUAL GROUP EVENT METHODS********************************
     *******************************************************************************************/

    public void createGroupTable(int GroupID){
        String CREATE_GROUP_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_GROUP_EVENTS + GroupID + "("
                + COLUMN_EVENTID + " INTEGER PRIMARY KEY," + COLUMN_LOCATION
                + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_OWNER + " Integer" + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_GROUP_EVENTS_TABLE);
        db.close();
    }



    public void addUserToGroup(int UserID, int GroupID){
       String members = getMembersFromGroup(GroupID);
        members += UserID + ":";
        String query = "UPDATE " + TABLE_GROUPS + " SET " + COLUMN_MEMBERS + "= \"" + members + "\""
                + " WHERE " + COLUMN_GROUPID + " = \"" + GroupID + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void removeUserFromGroup(int UserID, int GroupID){
        String curMembers = getMembersFromGroup(GroupID);
        String newMembers = curMembers.replace(UserID + ":", "");

        String query = "UPDATE " + TABLE_GROUPS + " SET " + COLUMN_MEMBERS + "= \"" + newMembers + "\""
                + " WHERE " + COLUMN_ID + " = \"" + GroupID + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }


    public void addEventToGroup(Event e, int GroupID) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENTID, e.getID());
        values.put(COLUMN_LOCATION, e.getLocation().toString());
        values.put(COLUMN_TIME, e.getTime());
        values.put(COLUMN_OWNER, e.getOwnerID());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_GROUP_EVENTS + GroupID, null, values);
        db.close();
    }


    public Event findEventInGroup(String location, int ID, int GroupID) {
        String query = "Select * FROM " + TABLE_GROUP_EVENTS + GroupID + " WHERE " + COLUMN_EVENTID + " =  \"" + ID
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
    public int getNumEventsInGroup(int GroupID){
        String query = "Select * FROM " + TABLE_GROUP_EVENTS + GroupID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        cursor.close();
        db.close();
        return Cursize;
    }
    public Event[] getEventsFromGroup(int GroupID){
        String query = "Select * FROM " + TABLE_GROUP_EVENTS + GroupID;
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

    public Event[] getEventsFromGroup(int GroupID, int UserID){
        String query = "Select * FROM " + TABLE_GROUP_EVENTS + GroupID;
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



    public boolean removeEventFromGroup(Event e, int GroupID){
        boolean result = false;
        String query = "Select * FROM " + TABLE_GROUP_EVENTS+ GroupID + " WHERE " + COLUMN_LOCATION +
                " =  \"" + e.getLocation().toString() + "\"" + " OR " + COLUMN_EVENTID + " =  \"" +  e.getID() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_GROUP_EVENTS + GroupID, COLUMN_EVENTID + " = ?",
                    new String[] { String.valueOf(Integer.parseInt(cursor.getString(0)))});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean removeEventsFromGroup(int GroupID ){
        boolean result = false;
        String query = "Select * FROM " + TABLE_GROUP_EVENTS + GroupID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int Cursize = cursor.getCount();
        Event[] events = new Event[Cursize+1];

        for (int i = 0; i<Cursize; i++){
            cursor.moveToPosition(i);
            db.delete(TABLE_GROUP_EVENTS + GroupID, COLUMN_EVENTID + " = ?",
                    new String[]{String.valueOf(Integer.parseInt(cursor.getString(0)))});
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }



}
