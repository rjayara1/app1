package objs;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.*;

/**
 * Created by Rajesh on 12/21/2015.
 */
public class Group implements Comparable<Group>{

    String name, description;
   SortedSet<User> Members, Admins;


    public Group(User creator){
    Members =  Collections.synchronizedSortedSet(new TreeSet<User>());
    Admins = Collections.synchronizedSortedSet(new TreeSet<User>());
    Admins.add(creator);
    Members.add(creator);

    }


    public int compareTo(Group x){
        String y = this.name;

        return y.compareTo(x.getName());
    }


    public String getName() {
        return name;
    }
}
