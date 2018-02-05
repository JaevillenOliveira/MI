/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Controller.Controller;
import Exceptions.AlreadyHasAdjacency;
import Exceptions.DuplicateEntryException;
import Exceptions.DuplicatedDataException;
import Exceptions.InexistentEntryException;
import Exceptions.InexistentVertexException;
import Exceptions.InsufficientSpotsException;
import Exceptions.LoopIsNotAllowedException;
import Exceptions.NotFoundException;
import Model.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jaevillen
 */
public class Facade {
    
    private Controller controller;
    
    public Facade () throws NoSuchAlgorithmException, UnsupportedEncodingException, DuplicatedDataException{
        controller = new Controller();
    }
    
    public User newUser (String name, String password, String cpf, String email) throws DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException{
        return controller.newUser(name, password, cpf, email);
    }
    
    public User doLogin(String cpf, String password) throws NotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException{
        return controller.doLogin(cpf, password);
    }
    
    public City addCity(String cityName, double latitude, double longitude, int code, double population) throws DuplicateEntryException{
        return controller.addCity(cityName, latitude, longitude, code, population);
    }
    
    public void addEatPoit(int code, String name, String adress, int rate) throws InexistentEntryException{
        controller.addEatPoint(code, name, adress, rate);
    }
    
    public City searchCity(int code) throws InexistentEntryException{
        return controller.searchCity(code);
    }
    
    public void addRoad (City cityA, City cityB, double km) throws DuplicateEntryException, AlreadyHasAdjacency, LoopIsNotAllowedException, InexistentVertexException, InexistentEntryException{
        controller.addRoad(cityA, cityB, km);
    }
    
    public void addRoad(Intersection inter,City city, double km) throws DuplicateEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException{
        controller.addRoad(inter, city, 0);
    }
    public void addRoad(Intersection interA, Intersection interB, double km) throws DuplicateEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException, InexistentVertexException, LoopIsNotAllowedException{
        controller.addRoad(interA, interB, 0);
    }
    
    public Trip startTrip(String cpf, String tripName) throws NotFoundException, DuplicateEntryException{
        return controller.startTrip(cpf, tripName);
    }
    
    public City insertCityInTrip(String cpf, String tripName, Calendar in, Calendar out, int code) throws InexistentEntryException, NotFoundException{
        return controller.insertCityInTrip(cpf, tripName, in, out, code);
    }
    
    public Iterator shortestPath(String cpf, String tripName) throws NotFoundException, InexistentEntryException, DuplicateEntryException, InsufficientSpotsException{
        return controller.shortestPath(cpf, tripName);
    }
    
    public List getCities(){
        return controller.getCities();
    }
    
}
