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
    
 

}
