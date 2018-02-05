/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Jaevillen
 */
public class Intersection extends Spot{
    
    private TypeIntersection type;

    public Intersection(TypeIntersection type, double latitude, double longitude, int code) {
        super(latitude, longitude, code);
        this.type = type;
    }
    
    public Intersection(int code){
        super (code);
    }

    public TypeIntersection getType() {
        return type;
    }
    
      @Override
    public int hashCode(){
        return 31 * this.getCode() * 47;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Intersection){
            Intersection inter = (Intersection)obj;
        
            if(this.getCode() == inter.getCode()){
                return true;
            }
        }
        
        return false;
    }
    
    
 

}
