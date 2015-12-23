package objs;
import java.util.*;

/**
 * Created by Rajesh on 12/21/2015.
 */
public class Event implements Comparable<Event>{

    Date time;
    Location loc;
    String descrip;
    SortedSet<User> Attendees;
    SortedSet<Group> Visibility;

    public Event(TimeZone tz, Date d, Location loc, String description ){
        time = d;
        this.loc = loc;
        descrip = description;
        Attendees =  Collections.synchronizedSortedSet(new TreeSet<User>());
    }

    public Date getTime() {
        return time;
    }


    public String getDescrip() {
        return descrip;
    }


    public Location getLoc() {
        return loc;
    }

    public void addAttendee(User x){
    Attendees.add(x);
    }

    public void removeAtendee(User x){
        Attendees.remove((User) x);
    }

    public String printAtendees(){
    String ret = "";
        Iterator<User> iter = Attendees.iterator();
        while(iter.hasNext()){
            ret = ret + iter.next().getUsername();
        }
    return ret;
    }


    public int compareTo(Event y){
        return this.time.compareTo(y.getTime());

    }



}
