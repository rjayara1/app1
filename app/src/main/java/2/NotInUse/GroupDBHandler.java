package NotInUse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import objs.Event;
import objs.Group;
import objs.Location;


public class GroupDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2; //TODO: figure out DB versions
    private static final String DATABASE_NAME = "AppDB.db";
    public int numGroups;


    //Info for Main Group Table
    private static final String TABLE_GROUPS = "Groups";
    public static final String COLUMN_GROUPID = "_id";
    public static final String COLUMN_GROUPNAME = "_name";
    public static final String COLUMN_MEMBERS = "_members";

    //Info for Individual Group Event Tables
    private static final String TABLE_GROUP_EVENTS= "GroupsEvents";
    public static final String COLUMN_EVENTID = "_id";
    public static final String COLUMN_LOCATION = "_location";
    public static final String COLUMN_TIME = "_time";
    public static final String COLUMN_OWNER = "_owner";

    public GroupDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_GROUPS_TABLE = "CREATE TABLE " +
                TABLE_GROUPS+ "("
                + COLUMN_GROUPID + " INTEGER PRIMARY KEY," + COLUMN_GROUPNAME
                + " TEXT," + COLUMN_MEMBERS + " TEXT" + ")";
        db.execSQL(CREATE_GROUPS_TABLE);

        Group[] groups = this.getGroups();
        for (int i = 0; i <groups.length; i++){
            if (groups[i]!= null) {
                String CREATE_GROUP_EVENTS_TABLE = "CREATE TABLE " +
                        TABLE_GROUP_EVENTS + groups[i].getGroupID() + "("
                        + COLUMN_EVENTID + " INTEGER PRIMARY KEY," + COLUMN_LOCATION
                        + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_OWNER + " Integer" + ")";
                db.execSQL(CREATE_GROUP_EVENTS_TABLE);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        onCreate(db);
    }

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
        }
        cursor.close();
        db.close();
        return groups;
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

