package objs;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.*;

/**
 * Created by Rajesh on 12/21/2015.
 */
public class Group implements Comparable<Group>{

    String name, description;
    int GroupID;
    //NOTE: Group.Members is a string of USER ID's representing the User Members of the group
    //Distinct ID's are seperated by colons, EX:
    //12345678:098765432:12131415:99998888  ... ect
    String Members;
    String Owner;



    public Group(String name, String description, int GroupID, String Members){
        this.name = name;
        this.GroupID = GroupID;
        this.Members = Members;
        this.description = description;


    }
    public Group(){
       name = "";
        GroupID = -1;
        Members = "";
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

    public String getMembers() {
        return Members;
    }

    public void setMembers(String members) {
        Members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public int compareTo(Group x){
        String y = this.name;

        return y.compareTo(x.getName());
    }
}
