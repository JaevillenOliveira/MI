/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.Entry;
import Exceptions.DuplicateEntryException;
import Exceptions.EmptyHashException;
import Exceptions.InexistentEntryException;
import java.util.Iterator;

/**
 *
 * @author AlmirNeto
 */
public class HashMap{
    
    private int size;
    private Entry[] map;
    private final double LOAD_FACTOR = 0.5;
    private final Entry EMPTY = new Entry(null, null);
    
    public HashMap(){
        map = new Entry[10];
    }
    
    public HashMap(int size){
        map = new Entry[size];
    }
    
    /**
     * 
     * Method that add an Entry in the HashSet.
     * @param key Object that is the key of the Hash.
     * @param value Object that is the value oh the Key.
     * @throws DuplicateEntryException When the Key is already in the Hash.
     */
    public void put(Object key, Object value) throws DuplicateEntryException{
        Entry entry = new Entry(key, value);
        int hashCode = Math.abs(key.hashCode() % map.length);
        
        if(!hasEntry(hashCode)){
            map[hashCode] = entry;
            size++;
        }
        else{
            searchPosition(hashCode, entry);
        }
        if((size / (double)map.length) > LOAD_FACTOR){
            resize();
        }
    }
    /**
     * Method that resize the Array.
     * 
     * @throws DuplicateEntryException 
     */
    private void resize() throws DuplicateEntryException{
        Entry[] temp = map;
        map = new Entry[map.length*2];
        for(Entry transf : temp){
            if(transf != null && !transf.equals(EMPTY)){
                put(transf.getKey(), transf.getValue());
            }
        }  
    }
    /**
     * Method that verifies if the position has an Entry.
     * @param position Position that will be verified.
     * @return TRUE if there's an Entry inside this position, otherwise returns false.
     */
    private boolean hasEntry(int position){
        return map[position] != null;
    }
    /**
     * Method that verifies where a new Entry will be if there's an Entry in its position.
     * @param position The hashCode of the current Entry.
     * @param search The Entry that will be added in the Hash.
     * @throws DuplicateEntryException When the Key is already in the Hash.
     */
    private void searchPosition(int position, Entry search) throws DuplicateEntryException{
        for(int i = position; i < map.length; i++){
            if(map[i] == null){
                map[i] = search;
                size++;
                return;
            }
            else if(map[i].equals(EMPTY)){
                map[i] = search;
                size++;
                return;
            }
            else if(map[i].equals(search)){
                throw new DuplicateEntryException();
            }
        }
    }
    /**
     * Method that removes a Key from the Hash.
     * @param key Key that will be removed from Hash.
     * @throws EmptyHashException
     * @throws InexistentEntryException 
     */
    public void remove(Object key) throws EmptyHashException, InexistentEntryException{
        if(size == 0){
            throw new EmptyHashException();
        }
        int hashCode = key.hashCode() % map.length;
        hashCode = Math.abs(hashCode);
        if(!hasEntry(hashCode)){
            throw new InexistentEntryException();
        }
        else if(map[hashCode].getKey().equals(key)){
            map[hashCode] = EMPTY;
            size--;
        }
        else{
            searchAndRemove(hashCode, key);
        }
    }
    /**
     * Method that Searches and removes the key from the Hash.
     * @param key Key that will be removed from the Hash.
     */
    private void searchAndRemove(int hashCode, Object key){
        for(int i = hashCode; i < map.length; i++){
            if(map[i].getKey().equals(key)){
                map[i] = EMPTY;
                size--;
            }
        }
    }
    /**
     * Method that verifies if a key it's in the Hash.
     * @param key The Key that will be verified.
     * @return TRUE if the Key is inside the Hash. FALSE, otherwise.
     * @throws InexistentEntryException When the key doesn't exist in Hash.
     */
    public boolean contains(Object key) throws InexistentEntryException{
        int hashCode = Math.abs(key.hashCode() % map.length);
        for(int i = hashCode; i < map.length; i++){
            if(map[i] == null){
                throw new InexistentEntryException();
            }
            if(map[i].getKey().equals(EMPTY)){
            }
            else if(map[i].getKey().equals(key)){
                return true;
            }
        }
        return false;
    }
    /**
     * Method that gets and return a Entry.
     * @param key Key that will ne searched in the Hash.
     * @return The Entry, if exists.
     * @throws InexistentEntryException When the key doesn't exist in Hash.
     */
    public Object get(Object key) throws InexistentEntryException{
        int hashCode = Math.abs(key.hashCode() % map.length);
        for(int i = hashCode; i < map.length; i++){
            if(map[i] == null){
                throw new InexistentEntryException();
            }
            else if(map[i].getKey().equals(EMPTY)){
            }
            else if(map[i].getKey().equals(key)){
                Object ret = map[i];
                return ret;
            }
        }
        throw new InexistentEntryException(); 
    }
    /**
     * Method that return the size of the Hash.
     * @return The size of the Hash.
     */
    public int size(){
        return size;
    }
    //Only for tests.
    public Iterator iterator() {
        return new myIt();
    }
    
    private class myIt implements Iterator{
        int position = 0;
        @Override
        public boolean hasNext() {
            return position < map.length;
        }

        @Override
        public Object next() {
            if(map[position] != null && !map[position].equals(EMPTY)){
                return map[position++];
            }
           
            position++;
            return null;
        }
    
    }
    
    public Entry[] toArray(){
        return map;
    }
  
    
}