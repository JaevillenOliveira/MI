/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Calendar;

/**
 *
 * @author Jaevillen
 */
public class PitStop{
    private City city;
    private Calendar timeIn;
    private Calendar timeOut;

    public PitStop(City city, Calendar timeIn, Calendar timeOut) {
        this.city = city;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Calendar getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Calendar timeIn) {
        this.timeIn = timeIn;
    }

    public Calendar getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Calendar timeOut) {
        this.timeOut = timeOut;
    }

}
