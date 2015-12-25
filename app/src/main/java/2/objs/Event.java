package objs;
import java.util.*;

/**
 * Created by Rajesh on 12/21/2015.
 */
public class Event implements Comparable<Event>{

    String time;
    int ID;
    Location loc;
    String descrip;
    SortedSet<User> Attendees;
    SortedSet<Group> Visibility;
    int ownerID;

    public Event( Location loc, String description, String d, int ID, int ownerIDD){
        time = d;
        this.loc = loc;
        descrip = description;
        Attendees =  Collections.synchronizedSortedSet(new TreeSet<User>());
        this.ID = ID;
        this.ownerID = ownerIDD;
    }

    public Event(Location loc, String description, String d,  int ID){
        time = d;
        this.loc = loc;
        descrip = description;
        Attendees =  Collections.synchronizedSortedSet(new TreeSet<User>());
        this.ID = ID;
    }

    public Event(){
        Attendees =  Collections.synchronizedSortedSet(new TreeSet<User>());
        ownerID = -1;
        time = "";
        loc = new Location("","");
        ID = -1;
        descrip = "";
    }



    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescrip() {
        return descrip;
    }


    public Location getLocation() {
        return loc;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
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

    //Earlier events come first
    public int compareTo(Event y){
       String[] yTime = y.getTime().split(":");
        String[] xTime = this.getTime().split(":");
        String[] ytime = yTime[1].split("\\s");
        String[] xtime = xTime[1].split("\\s");
        int yHour = Integer.parseInt(yTime[0]);
        int yMin = Integer.parseInt(ytime[0]);
        String yAMPM = ytime[1];
        int xHour = Integer.parseInt(xTime[0]);
        int xMin = Integer.parseInt(xtime[0]);
        String xAMPM = xtime[1];

        if (yAMPM.equals("PM")){
            if (yHour < 12){
                yHour += 12;
            }
        }
        if (yAMPM.equals("AM")) {
            if (yHour == 12) {
            yHour = 0;
            }
        }
        if (xAMPM.equals("PM")){
            if (xHour < 12){
                xHour += 12;
            }
        }
        if (xAMPM.equals("AM")) {
            if (xHour == 12) {
                xHour = 0;
            }
        }
        int xTotalMinutes = xHour*60 + xMin;
        int yTotalMinutes = yHour*60 + yMin;
        return (xTotalMinutes - yTotalMinutes);


    }



}
