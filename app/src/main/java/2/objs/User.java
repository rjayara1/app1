package objs;
import java.lang.reflect.Member;
import java.util.*;
import java.net.*;
import android.net.*;


import com.example.rajesh.app1.MyDBHandler;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Rajesh on 12/21/2015.
 */
public class User implements Comparable<User> {
    String Username;
    String Password;
    String Friends;
    String FriendRequests;
    PriorityBlockingQueue<Event> mySched, friendSched; //Handled server side
    int ID;


    public User(String name, String Pass, int id,String Friends, String FriendRequests){
        this.Username = name;
        Password = Pass;
        this.ID = id;
        this.Friends = Friends;
        this.FriendRequests = FriendRequests;
    }
    public User(String name, String Pass, int id){
        this.Username = name;


        Password = Pass;
        this.ID = id;
    }
    public User(String name, String Pass){
        this.Username = name;

        Password = Pass;

    }

    public User(){

    }

    public String getFriends() {
        return Friends;
    }
    public void setFriends(String friends) {
        Friends = friends;
    }
    public String getFriendRequests() {
        return FriendRequests;
    }
    public void setFriendRequests(String friendRequests) {
        FriendRequests = friendRequests;
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

    public int compareTo(User x){
        String y = this.Username;
        return y.compareTo(x.getUsername());
    }



}
