package objs;
import java.lang.reflect.Member;
import java.util.*;
import java.net.*;
import android.net.*;


import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Rajesh on 12/21/2015.
 */
public class User implements Comparable<User> {
    String Username;
    String Password;
    SortedSet<User> friends;
    SortedSet<Group> MemberOf;
    PriorityBlockingQueue<Event> mySched, friendSched; //Handled server side
    int ID;


    public User(String name, String Pass, int id){
        this.Username = name;
        friends = Collections.synchronizedSortedSet(new TreeSet<User>());
        MemberOf = Collections.synchronizedSortedSet(new TreeSet<Group>());
        mySched = new PriorityBlockingQueue<Event>();
        friendSched = new PriorityBlockingQueue<Event>();
        Password = Pass;
        this.ID = id;
    }
    public User(String name, String Pass){
        this.Username = name;
        friends = Collections.synchronizedSortedSet(new TreeSet<User>());
        MemberOf = Collections.synchronizedSortedSet(new TreeSet<Group>());
        mySched = new PriorityBlockingQueue<Event>();
        friendSched = new PriorityBlockingQueue<Event>();
        Password = Pass;

    }

    public User(){


    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String name) {
        this.Username = name;
    }

    public void setFriends(SortedSet<User> friends) {
        this.friends = friends;
    }

    public void setMemberOf(SortedSet<Group> memberOf) {
        MemberOf = memberOf;
    }

    public int compareTo(User x){
        String y = this.Username;
        return y.compareTo(x.getUsername());
    }

    public SortedSet<User> getFriends() {
        return friends;
    }

    public void addEvent(Event e){
        //TODO: set up network to add event to server, use local database for now
        mySched.add(e);

    }

    public void getMainSched(){
        //TODO: Import schedule from database

    }

}
