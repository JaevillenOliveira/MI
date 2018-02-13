/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import Exceptions.ThereNoKeysException;
import Exceptions.TheresNoCityException;
import Exceptions.TheresNoInterException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author AlmirNeto
 */
public class Map extends JPanel implements Serializable{
    
    private static Graphics2D graphic;
    private static Controller control;
    
    public Map(Controller control){
        this.control = control;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphic = (Graphics2D)g;
        
        LinkedList<City> cities = null;
        LinkedList<Intersection> inters = null;
        LinkedList<Edge> roads = null;
        
        try {
            cities = control.getCities();
        } catch (TheresNoCityException ex) {
        }
        for(City city : cities){
            graphic.setPaint(Color.BLACK);
            Ellipse2D.Double elipse = new Ellipse2D.Double(Math.abs(city.getLatitude()*10), Math.abs(city.getLongitude()*10), 8, 8);
            graphic.fill(elipse);
        }
        
        try{
            inters = control.getInter();
        } catch (TheresNoInterException ex) {
        }
        
        for(Intersection inter : inters){
            if(inter.getType() == TypeIntersection.ROTULA){
            graphic.setPaint(Color.GREEN);
            Ellipse2D.Double elipse = new Ellipse2D.Double(Math.abs(inter.getLatitude()*10), Math.abs(inter.getLongitude()*10), 8, 8);
            graphic.fill(elipse);
        }
        else if(inter.getType() == TypeIntersection.SEMAPHORE){
            graphic.setPaint(Color.RED);
            Ellipse2D.Double elipse = new Ellipse2D.Double(Math.abs(inter.getLatitude()*10), Math.abs(inter.getLongitude()*10), 8, 8);
            graphic.fill(elipse);
        }
        else if(inter.getType() == TypeIntersection.CROSSING){
            graphic.setPaint(Color.YELLOW);
            Ellipse2D.Double elipse = new Ellipse2D.Double(Math.abs(inter.getLatitude()*10), Math.abs(inter.getLongitude()*10), 8, 8);
            graphic.fill(elipse);
        }
        }
        
        try{
            roads = control.getRoads();
        } catch (ThereNoKeysException ex) {
        }
        
        for(Edge edge : roads){
            Spot spotA = (Spot)edge.getA().getVertex();
            Spot spotB = (Spot)edge.getB().getVertex();
            graphic.setPaint(Color.BLACK);
            Line2D.Double line = new Line2D.Double(Math.abs(spotA.getLatitude()*10), Math.abs(spotA.getLongitude()*10), Math.abs(spotB.getLatitude()*10), Math.abs(spotB.getLongitude()*10));
            graphic.draw(line);
        }   
    } 
}
