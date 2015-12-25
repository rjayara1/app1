package objs;

/**
 * Created by Rajesh on 12/21/2015.
 */
public class Location {

    String Description;
    String loc; //location

    public Location(String location, String description){
        this.Description = description;
        this.loc = location;
    }
    public Location(String location){
        this.Description = "";
        this.loc = location;
    }


    public String getLoc() {
        return loc;
    }

    @Override
    public String toString() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
