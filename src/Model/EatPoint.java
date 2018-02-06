
package Model;

/**
 *
 * @author AlmirNeto
 */
public class EatPoint {
    
    private String name;
    private String adress;
    private Rate rate;
    
    public EatPoint(String name, String adress){
        this.name = name;
        this.adress = adress; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
    
    
}
