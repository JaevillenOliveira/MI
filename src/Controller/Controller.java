
package Controller;

import Exceptions.TheresNoEntryException;
import Exceptions.TheresNoCityException;
import Exceptions.DontHaveTripsException;
import Exceptions.*;
import Model.*;
import Util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaevillen and Almir
 */
public class Controller {
   
    private final Tree users; //HashSet with the logins of the Users.
    private final Graph cities; //HashSet with the cities points.

    /**
     * Constructor of the class
     */
    public Controller() throws NoSuchAlgorithmException, UnsupportedEncodingException, DuplicatedDataException{
        
        users = new Tree();
        cities = new Graph();
        addAdmin();
    }

    /**
     * Method that add a new User in the Hash.
     * 
     * @param name The name of the User.
     * @param password The password of the User.
     * @param cpf The CPF of the User
     * @param email The e-Mail of the User.
     * @return The User after Created.
     * @throws DuplicatedDataException If there's already an User with the Login.
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public User newUser(String name, String password, String cpf, String email) throws DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException{

        User newUser = new User(this.hashPassword(password), name, cpf, email, UserType.USER);
        
        try{
            alreadyIsUser(newUser);
            throw new DuplicatedDataException();
        }
        catch(NotFoundException nfx){
            users.inserir(newUser);
            return newUser;
        }         
    }
    /**
     * Method that tries to do Login to an User.
     * 
     * @param cpf CPF of the User.
     * @param password Password of the User.
     * @return TRUE If the User exists and the Password is correct, FALSE otherwise.
     * @throws NotFoundException If the User doesn't exist.
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public User doLogin(String cpf, String password) throws NotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException{
        User user = new User(cpf, password);
        
        if(alreadyIsUser(user)){
            User verifyUser = getUser(cpf);

            if(verifyUser.getPassword().equalsIgnoreCase(this.hashPassword(password))){
                return verifyUser;
            }
        }
        return null;
    }
    
    /**
     *
     * Method that encrypts password of the user
     * 
     * @param password Password choosen by the user.
     * @return the sequence that will be stored like password of the user.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte hashPassword [] = messageDigest.digest(password.getBytes("UTF-8"));
        
        return Arrays.toString(hashPassword);
    }
    /**
     * Method that verifies if a User is already in Tree.
     * 
     * @param user User that wll be verified.
     * @return TRUE if the User is already registered in the System.
     * @throws InexistentEntryException When the User isn't registered in the system.
     */
    private boolean alreadyIsUser(User user) throws NotFoundException{
        User verifyUser = (User)users.buscar(user);
        return true;
    }
    /**
     * Method that get an User in Tree and returns It.
     * @param cpf CPF of the User that will be searched.
     * @return The User with that CPF if exists.
     * @throws NotFoundException If the User with that CPF doesn't exist.
     */
    private User getUser(String cpf) throws NotFoundException{
        User user = new User(cpf);
        
        User ret = (User)users.buscar(user);
        
        return ret;
    }

    /**
     * Method that add a City into the Graph.
     * @param cityName The Name of the City.
     * @param latitude Latitude of the City.
     * @param longitude Longitude of the City.
     * @param code The Code of the City.
     * @param population The City's Population.
     * @return The City after created.
     * @throws DuplicateEntryException If has already a City with this Name.
     */
    public City addCity(String cityName, double latitude, double longitude, int code, double population) throws DuplicateEntryException{
        
        City city = new City(cityName.toUpperCase(), latitude, longitude, code);
        cities.addVertex(city); 
        
        return city;
    }
    
    
    /**
     * Method that add a Intersection into the Graph
     * @param type The type of the Intersection.
     * @param latitude Latitude of the Intersection.
     * @param longitude Longitude if the Intersection.
     * @param code The code of the Intersection.
     * @return The Intersection created.
     * @throws DuplicateEntryException If has already a Intersection with the code informed.
     */
    public Intersection addIntersection (TypeIntersection type, double latitude, double longitude, int code) throws DuplicateEntryException{
        Intersection intersection = new Intersection(type, latitude, longitude, code);
        
        cities.addVertex(intersection);
        
        return intersection;
    }
    
    /**
     * Method that Add a Point to Eat in a City.
     * @param code The Code of the city that the EatPoint is located.
     * @param name The Name of the Place.
     * @param adress The Adress of the Place.
     * @param rate The Rate of the Place.
     * @throws InexistentEntryException If the City with this code doesn't exist.
     */
    public void addEatPoint(int code, String name, String adress, int rate) throws InexistentEntryException{
        EatPoint eat = new EatPoint(name, adress);
        
        if(rate == 1){
            eat.setRate(Rate.PÉSSIMO);
        }
        else if(rate == 2){
            eat.setRate(Rate.RUIM);
        }
        else if(rate == 3){
            eat.setRate(Rate.REGULAR);
        }
        else if(rate == 4){
            eat.setRate(Rate.BOM);
        }
        else if(rate == 5){
            eat.setRate(Rate.ÓTIMO);
        }
        City search = new City(code);

        City addEat = (City)cities.getKey(search);
        
        if(addEat.getPlaceEat() == null){
            addEat.setPlaceEat(new ArrayList());
            addEat.getPlaceEat().add(eat);
        }
        
        else{
            addEat.getPlaceEat().add(eat);
        }
    }
    /**
     * Method that Add a Road Between two Cities.
     * 
     * @param cityA The First City.
     * @param cityB The Second City.
     * @param km The lenght of the Road.
     * @throws DuplicateEntryException If there's already a Road between these Cities.
     * @throws AlreadyHasAdjacency If there's already a Road between these Cities.
     * @throws InexistentVertexException If one of the Cities doesn't exist.
     * @throws LoopIsNotAllowedException If the Codes are headed for the same City.
     * @throws Exceptions.InexistentEntryException
     */
    public void addRoad(City cityA, City cityB, double km) throws AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException, InexistentEntryException, DuplicateEntryException{
        cities.addEdge(cityA, cityB, km);
    }
    
     /**
     * Method that adds a Road between a City and an Intersection.
     * 
     * @param inter The Intersection.
     * @param city The City.
     * @param km The lenght of the Road.
     * @throws DuplicateEntryException If there's already a Road between these Points.
     * @throws AlreadyHasAdjacency If there's already a Road between these Points.
     * @throws InexistentVertexException If one of the Points doesn't exist.
     * @throws LoopIsNotAllowedException If the Codes are headed for the same Point.
     */
    public void addRoad (Intersection inter,City city, double km) throws DuplicateEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException{
        cities.addEdge(inter,city, km);
    }
    
      /**
     * Method that adds a Road between a two Intersections.
     * 
     * @param interA The firs Intersection.
     * @param interB The second Intersection.
     * @param km The lenght of the Road.
     * @throws DuplicateEntryException If there's already a Road between these Points.
     * @throws AlreadyHasAdjacency If there's already a Road between these Points.
     * @throws InexistentVertexException If one of the Points doesn't exist.
     * @throws LoopIsNotAllowedException If the Codes are headed for the same Point.
     */
    public void addRoad (Intersection interA, Intersection interB, double km) throws DuplicateEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException{
        cities.addEdge(interA, interB, km);
    }
    
    /**
     * Method that creates a Trip.
     * @param cpf The CPF of the user.
     * @param tripName The name identifies the trip.
     * @param date The Date of the Start of the Trip 
     * @return The trip.
     * @throws DuplicateEntryException If there's already a trip with the name informed.
     * @throws NotFoundException If the user doesn't exist.
     */
    public Trip startTrip(String cpf, String tripName, Calendar date) throws DuplicateEntryException, NotFoundException{
        
        User user = getUser(cpf);
        
        Trip trip = new Trip(tripName.toUpperCase());
        trip.setInitialDate(date);
        
        if(user.getTrip() == null){
            user.setTrip(new Queue());
            user.getTrip().put(trip);
        }
        else{
            if(user.getTrip().contains(trip)){
                throw new DuplicateEntryException();
            }
            user.getTrip().put(trip);
        }
        return trip;
    }
    
     /**
     * Method that search a City for your code. 
     * 
     * @param code The code of the City.
     * @return The City found.
     * @throws InexistentEntryException If there's no City in the Graph with the code informed.
     */
    public City searchCity(int code) throws InexistentEntryException{
        City city = new City(code);
        
        City realCity = (City) cities.getKey(city);
        
        return realCity;
    }
    
    /**
     * Method that search a Intersection for your code. 
     * 
     * @param code The code of the Intersection.
     * @return The Intersection found.
     * @throws InexistentEntryException If there's no Intersection in the Graph with the code informed.
     */
    public Intersection searchIntersection(int code) throws InexistentEntryException{
        Intersection inter = new Intersection(code);
        
        Intersection realInter = (Intersection) cities.getKey(inter);
        
        return realInter;
    }
    
    
    /**
     * Method that search a trip by the name.
     * @param cpf The CPF of the user.
     * @param tripName The name of the trip searched.
     * @return The trip.
     * @throws NotFoundException If doesn't exist a trip with the name informed.    
     */
    public Trip searchTrip(String cpf, String tripName) throws NotFoundException{

        User user = getUser(cpf);
        
        Trip trip = new Trip(tripName);
        if(user.getTrip().contains(trip)){
            Iterator it = user.getTrip().iterator();
            while(it.hasNext()){
                Trip test = (Trip) it.next();
                if(test.getName().equalsIgnoreCase(tripName)){
                    return test;
                }
            }
        }
        throw new NotFoundException();
    }
    
    /**
     * Method that insert a stop city in the trip
     * @param cpf The CPF of the user. 
     * @param tripName The name of the trip to be changed
     * @param in The arrival date in the city. 
     * @param out The departure date.
     * @param code The code of the stop city. 
     * @return The stop city.
     * @throws InexistentEntryException If doesn't exist city with the name informed.
     * @throws NotFoundException If doesn't exist trip with the name informed.
     */
    public City insertCityInTrip(String cpf, String tripName, Calendar in, Calendar out, int code) throws InexistentEntryException, NotFoundException{
        City realCity = this.searchCity(code);
       
        Trip trip = this.searchTrip(cpf, tripName);
        
        PitStop ps = new PitStop(realCity, in, out);
        
        trip.getSpots().add(ps);
        
        return realCity;
    }
    
   /**
     * Method that finds the shortest path for the Trip.
     * @param cpf The CPF of the user.
     * @param tripName The name of the Trip registered.
     * @return The Iterator of the ArrayList with the points of the shortest route.
     * @throws NotFoundException If there's not a Trip or an User registered in the system with the data informed.
     * @throws InexistentEntryException If there some City in the Trip that doesn't exists in the Graph.
     * @throws DuplicateEntryException 
     * @throws InsufficientSpotsException If the Trip don't has a least two points.
     */
    public Iterator shortestPath (String cpf, String tripName) throws NotFoundException, InexistentEntryException, DuplicateEntryException, InsufficientSpotsException, TheresNoEntryException, NoWaysException{

        Trip trip = this.searchTrip(cpf, tripName);
        ArrayList spots = trip.getSpots();
        if(spots.size() < 2){
            throw new InsufficientSpotsException();
        }
        
        Iterator it = spots.iterator();
        
        City start = null; 
        City end = null;
        
        ArrayList <EntryDjikstra> finalPath = new ArrayList();
        while(it.hasNext()){
            end = (City) ((PitStop) it.next()).getCity();
            if(start != null){
                Stack itStack = cities.path(start, end, cities.shortestPath(start));
                while(!itStack.empty()){
                    finalPath.add((EntryDjikstra) itStack.pop());  
                } 
            }
            start = end;  
        }
        return finalPath.iterator();
    } 
    
    private void addAdmin() throws NoSuchAlgorithmException, UnsupportedEncodingException, DuplicatedDataException{
        
        String pw = hashPassword("pbl4");
        User admin = new User("admin", pw);
        
        users.inserir(admin);
    }
    /**
     * Method that returns all Cities of the System.
     * @return All Cities of The System.
     * @throws TheresNoCityException When there's no City in the System. 
     */
    public LinkedList getCities() throws TheresNoCityException{
        
        LinkedList<Vertex> city;
        try{
            city = cities.getAllVertex();
        } catch (ThereNoKeysException ex) {
            throw new TheresNoCityException();
        }
       
        LinkedList casted = new LinkedList();
        
        for(Vertex vertex : city){
            casted.add(vertex.getVertex());
        }
        return casted;
    }
    /**
     * Method that verifies if there are Cities in System.
     * @return TRUE If there are City in System.
     */
    public boolean haveCities(){
        try{
            cities.getAllVertex();
            return true;
        } catch (ThereNoKeysException ex) {
            return false;
        }
    }
    /**
     * Method that returns the Trips from an User.
     * @param user The User that will be verified if there aew Trips.
     * @return A LinkedList with all User's Trips.
     */
    public LinkedList getUserTrips(User user){
        
        LinkedList trips;
        if(haveTrips(user)){
            trips = user.getTrip().toList();
            return trips;
        }
        return null;
    }
    /**
     * Method that verifies if an User has Trips registered.
     * @param user The User that will be verified if there are Trips.
     * @return TRUE if there's the User has Trips.
     */
    public boolean haveTrips(User user){
        if(user.getTrip() == null){
            return false;
        }
        else{
            return true;
        }
    }
    /**
     * Method that remove a City from a Trip
     * @param trip The Trip that the city will be removed.
     * @param code The Code of the City that will be removed.
     * @throws InexistentEntryException If the City with the Code doesn't exist.
     */
    public void removeCityFromTrip(Trip trip, int code) throws InexistentEntryException{
        
        ArrayList<PitStop> spots = trip.getSpots();
        City city = searchCity(code);
        
        PitStop remove = new PitStop(city);
        
        spots.remove(remove);
    }
    
    public void readEdges(String directory) throws FileNotFoundException, InexistentEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException, DuplicateEntryException{
        File arq = new File(directory);
        Scanner scan = new Scanner(arq);
        
        while(scan.hasNext()){
            int codeA = scan.nextInt();
            int codeB = scan.nextInt();
            double km = Double.parseDouble(scan.next());
           
            City cityA = null, cityB = null;
            Intersection interA = null, interB = null;
            try{
                cityA = this.searchCity(codeA);
            }catch(InexistentEntryException ex){
                interA = this.searchIntersection(codeA);
            }
            
           try{
                cityB = this.searchCity(codeB);
            }catch(InexistentEntryException ex){
                interB = this.searchIntersection(codeB);
            }
           
            if (cityA != null && cityB != null){
                this.addRoad(cityA, cityB, km);
            }
            else if(interA != null && interB != null){
                this.addRoad(interA, interB, km);
            }
            else if(cityA != null && interB != null) {
                this.addRoad(interB, cityA, km);
            }
            else{
                this.addRoad(interA, cityB, km);
            }
        }
        scan.close();
        
    }
    
    public void readCities(String directory) throws FileNotFoundException, DuplicateEntryException{
        File arq = new File(directory);
        Scanner scan = new Scanner(arq);
        
        scan.useDelimiter("\u0009");

        while(scan.hasNext()){
            String nome = scan.next();
            int code = scan.nextInt();
            double latitude = Double.parseDouble(scan.next());
            double longitude = Double.parseDouble(scan.next());

            this.addCity(nome.substring(nome.indexOf("\n") + 1), latitude, longitude, code, 0);
        }
        
        
    }
        
}
