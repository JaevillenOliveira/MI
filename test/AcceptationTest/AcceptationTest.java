/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcceptationTest;

import Controller.Controller;
import Exceptions.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import Model.*;
import Util.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Iterator;

/**
 *
 * @author almir
 */
public class AcceptationTest {
    
    private Controller controller;
    private City capela, novaFatima, riachao, itatiaia, gaviao, saoJose, capimGrosso;
    
    public AcceptationTest() {
        controller = new Controller();
    }
    
    @Before
    public void setUp() {
        
        
    }
    
    @Test
    public void testNewUser() throws DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException{
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
    }
    
    @Test (expected = DuplicatedDataException.class)
    public void testDuplicatedUsers() throws DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException{
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
       
        controller.newUser("Luciano Araujo", "12345", "123456789", "teste@gmail.com");
    }
    @Test
    public void testRegisterOfTwoDifferentUsers() throws DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException{
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
        
        controller.newUser("Luciano Araujo", "12345", "987654321", "teste@gmail.com");
        
    }
    
    @Test
    public void testLogin() throws DuplicatedDataException, NotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException{
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
        
        boolean test = controller.doLogin("123456789", "123456");
        
        Assert.assertEquals(true, test);
        
    }
    
    @Test
    public void testAddCity() throws DuplicateEntryException, InexistentEntryException, NotFoundException, InvalidSpotException{
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        novaFatima = controller.addCity("Nova Fátima", 22.2, 12.1, 301);
        gaviao =  controller.addCity("Gavião", 22.2, 12.1, 310);
        saoJose = controller.addCity("São José", 22.2, 12.1, 350);
        riachao = controller.addCity("Riachão", 22.2, 12.1, 400);
        capimGrosso = controller.addCity("Capim Grosso", 22.2, 12.1, 402);
        itatiaia = controller.addCity("Itatiaia", 22.2, 12.1, 500);
        
        Assert.assertEquals("CAPELA",capela.getName());
        Assert.assertEquals("NOVA FÁTIMA", novaFatima.getName());
        Assert.assertEquals("GAVIÃO", gaviao.getName());
        Assert.assertEquals("SÃO JOSÉ",saoJose.getName() );
        Assert.assertEquals("RIACHÃO", riachao.getName());
        Assert.assertEquals("CAPIM GROSSO", capimGrosso.getName());
        Assert.assertEquals("ITATIAIA", itatiaia.getName());
    }
    
    @Test(expected = DuplicateEntryException.class)
    public void testAddDuplicatedCity() throws DuplicateEntryException{
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        
        capimGrosso = controller.addCity("Capim Grosso", 22.2, 12.1, 300);
    }
    
    @Test
    public void testAddEatPoint() throws DuplicateEntryException, InexistentEntryException{
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        
        controller.addEatPoint(300, "Módulo 8", "Próximo a UEFS", 2);
    
    }
    
    @Test
    public void testIfTheEatPointIsRegistered() throws InexistentEntryException, DuplicateEntryException{
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        
        controller.addEatPoint(300, "Módulo 8", "Próximo a UEFS", 2);
        
        EatPoint ep = capela.getPlaceEat().get(0);
        
        Assert.assertEquals("Módulo 8", ep.getName());
    }
    
    @Test (expected = InexistentEntryException.class)
    public void testRegisterAnEatPointIntoACityThatDoesntExist() throws InexistentEntryException{
        
        controller.addEatPoint(300, "Módulo 8", "Próximo a UEFS", 2);
    }
    
    @Test
    public void testAddRoadBetweenTwoCities() throws DuplicateEntryException, InexistentEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException{
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        
        gaviao =  controller.addCity("Gavião", 22.2, 12.1, 310);
        
        controller.addRoad(300, 310, 50);
    }
    
    @Test (expected = LoopIsNotAllowedException.class)
    public void testAddRoadBetweenTheSameCity() throws DuplicateEntryException, InexistentEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException{
        
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        
        controller.addRoad(300, 300, 50);
    }
    
    @Test (expected = InexistentVertexException.class)
    public void testAddRoadBetweenACityThatDoesntExist() throws InexistentEntryException, DuplicateEntryException, AlreadyHasAdjacency, LoopIsNotAllowedException, InexistentVertexException{
        
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        
        controller.addRoad(300, 13247, 50);
    }
    
    @Test (expected = AlreadyHasAdjacency.class)
    public void testAddRoadBetweenTwoCitiesThatHaveAlreadyAdjacency() throws InexistentEntryException, DuplicateEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException{
        
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        
        gaviao =  controller.addCity("Gavião", 22.2, 12.1, 310);
        
        controller.addRoad(300, 310, 50);
        
        controller.addRoad(310, 300, 150);
    }
    
    @Test
    public void testStartATrip() throws DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException, DuplicateEntryException, NotFoundException{
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
        
        controller.startTrip("123456789", "Rolê pra Cabuçu");
    }
    
    @Test (expected = DuplicateEntryException.class)
    public void testStartATripWithTheSameName() throws DuplicateEntryException, DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException, NotFoundException{
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
        
        controller.startTrip("123456789", "Rolê pra Cabuçu");
        
        controller.startTrip("123456789", "Rolê pra Cabuçu");
        
    }
    
    @Test (expected = InexistentEntryException.class)
    public void testInsertSpotsInexistentsInTheTrip() throws DuplicateEntryException, DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException, NotFoundException, InexistentEntryException{
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
        
        controller.startTrip("123456789", "Rolê pra Cabuçu");
        Calendar data = Calendar.getInstance();
        
        controller.insertCityInTrip("123456789", "Rolê pra Cabuçu",data , data, 300);
    }
    
    @Test 
    public void testInsertSpotsInTheTripWithSucess() throws DuplicateEntryException, DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException, NotFoundException, InexistentEntryException{
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
        
        controller.startTrip("123456789", "Rolê pra Cabuçu");
        Calendar data = Calendar.getInstance();
        
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        
        City city = controller.insertCityInTrip("123456789", "Rolê pra Cabuçu",data , data, 300);
        
        Assert.assertEquals(capela, city);
    }
    
    @Test
    public void testShortestPath() throws DuplicateEntryException, NotFoundException, DuplicatedDataException, NoSuchAlgorithmException, UnsupportedEncodingException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException, InexistentEntryException, InsufficientSpotsException{
        
        capela = controller.addCity("Capela", 22.2, 12.1, 300);
        novaFatima = controller.addCity("Nova Fátima", 22.2, 12.1, 301);
        gaviao =  controller.addCity("Gavião", 22.2, 12.1, 310);
        saoJose = controller.addCity("São José", 22.2, 12.1, 350);
        riachao = controller.addCity("Riachão", 22.2, 12.1, 400);
        capimGrosso = controller.addCity("Capim Grosso", 22.2, 12.1, 402);
        itatiaia = controller.addCity("Itatiaia", 22.2, 12.1, 500);
        
        controller.newUser("Almir Neto", "123456", "123456789", "teste@gmail.com");
        
        controller.startTrip("123456789", "Rolê pra Cabuçu");

        controller.addRoad(301, 300, 20.0);
        controller.addRoad(310, 301, 30.0);
        controller.addRoad(310, 300, 50.5);
        controller.addRoad(402, 300, 34.2);
        
        Calendar data = Calendar.getInstance();
        
        City city = controller.insertCityInTrip("123456789", "Rolê pra Cabuçu",data , data, 310);       
        Assert.assertEquals(gaviao, city);
        city = controller.insertCityInTrip("123456789", "Rolê pra Cabuçu",data , data, 300);
        Assert.assertEquals(capela, city);
        city = controller.insertCityInTrip("123456789", "Rolê pra Cabuçu",data , data, 402);
        
       Iterator it = controller.shortestPath("123456789", "Rolê pra Cabuçu");

        
    }
}