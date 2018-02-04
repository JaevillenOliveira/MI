
package Model;

/**
 *
 * @author Jaevillen and Almir
 */
public class Spot {
    
    private double latitude;
    private double longitude;
    private int code;

    public Spot(double latitude, double longitude, int code) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.code = code;
    }

    public Spot(int code){
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    
}
