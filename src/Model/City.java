
package Model;

import java.util.ArrayList;

/**
 *
 * @author Jaevillen and Almir
 */
public class City extends Spot{
    
    private String name;
    private double population;
    private ArrayList<EatPoint> placeEat;

    public City(String name, double latitude, double longitude, int code) {
        super(latitude, longitude, code);
        this.name = name;
    }

    public void setPopulation(double population) {
        this.population = population;
    }
    
    public City(int code){
        super(code);
    }

    public String getName() {
        return name;
    }

    public double getPopulation() {
        return population;
    }

    public ArrayList<EatPoint> getPlaceEat() {
        return placeEat;
    }

    public void setPlaceEat(ArrayList<EatPoint> placeEat) {
        this.placeEat = placeEat;
    }
    
    @Override
    public int hashCode(){
        return 31 * this.getCode() * 47;
    }
    
    @Override
    public boolean equals(Object obj){
        City city = (City)obj;
        
        if(this.getCode() == city.getCode()){
            return true;
        }
        return false;
    }
}
