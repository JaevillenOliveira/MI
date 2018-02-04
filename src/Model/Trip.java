
package Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Jaevillen and Almir
 */
public class Trip{
    
    private ArrayList<PitStop> spots;
    private Calendar initialDate;
    private String name;
    
    public Trip(String name){
        this.name = name;
        this.spots = new ArrayList();
    }

    public ArrayList<PitStop> getSpots() {
        return spots;
    }

    public void setSpots(ArrayList<PitStop> spots) {
        this.spots = spots;
    }

    public Calendar getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Calendar initialDate) {
        this.initialDate = initialDate;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj){
        Trip trip = (Trip)obj;
        if(this.name.equalsIgnoreCase(trip.name)){
            return true;
        }
        return false;
    }
}
