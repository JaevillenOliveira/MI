
package Util;

import Model.Entry;
import Exceptions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author almir
 */
public class HashSet{
    
    private int size;
    private Entry[] keys;
    private final double LOAD_FACTOR = 0.5;
    private final Entry EMPTY = new Entry(null, null);
    
    public HashSet(){
        keys = new Entry[10];
    }
    
    public HashSet(int size){
        keys = new Entry[size];
    }
    /**
     * 
     * Method that add an Entry in the HashSet.
     * @param key Object that is the key of the Hash.
     * @throws DuplicateEntryException When the Key is already in the Hash.
     */
    public void put(Object key) throws DuplicateEntryException{
        Entry new_key = new Entry(key, null);
        int hashCode = Math.abs(key.hashCode() % keys.length);
        
        if(!hasEntry(hashCode)){
            keys[hashCode] = new_key;
            size++;
        }
        else{
            searchPosition(hashCode, new_key);
        }
        if((size() / (double)keys.length) > LOAD_FACTOR){
            resize();
        }
    }
    /**
     * Method that resize the Array.
     * 
     * @throws DuplicateEntryException 
     */
    private void resize() throws DuplicateEntryException{
        Entry[] temp = keys;
        keys = new Entry[keys.length*2];
        for(Entry transf : temp){
            if(transf != null && !transf.equals(EMPTY)){
                put(transf.getKey());
            }
        }  
    }
    /**
     * Method that verifies if the position has an Entry.
     * @param position Position that will be verified.
     * @return TRUE if there's an Entry inside this position, otherwise returns false.
     */
    private boolean hasEntry(int position){
        return keys[position] != null;
    }
    /**
     * Method that verifies where a new Entry will be if there's an Entry in its position.
     * @param position The hashCode of the current Entry.
     * @param search The Entry that will be added in the Hash.
     * @throws DuplicateEntryException When the Key is already in the Hash.
     */
    private void searchPosition(int position, Entry search) throws DuplicateEntryException{
        for(int i = position; i < keys.length; i++){
            if(keys[i] == null){
                keys[i] = search;
                size++;
                return;
            }
            else if(keys[i].equals(EMPTY)){
                keys[i] = search;
                size++;
                return;
            }
            else if(keys[i].equals(search)){
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
        int hashCode = key.hashCode() % keys.length;
        hashCode = Math.abs(hashCode);
        if(!hasEntry(hashCode)){
            throw new InexistentEntryException();
        }
        else if(keys[hashCode].getKey().equals(key)){
            keys[hashCode] = EMPTY;
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
        for(int i = hashCode; i < keys.length; i++){
            if(keys[i].getKey().equals(key)){
                keys[i] = EMPTY;
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
        int hashCode = Math.abs(key.hashCode() % keys.length);
        for(int i = hashCode; i < keys.length; i++){
            if(keys[i] == null){
                throw new InexistentEntryException();
            }
            if(keys[i].getKey().equals(EMPTY)){
            }
            else if(keys[i].getKey().equals(key)){
                return true;
            }
        }
        return false;
    }
    /**
     * Method that gets and return a Key.
     * @param key Key that will ne searched in the Hash.
     * @return The key, if exists.
     * @throws InexistentEntryException When the key doesn't exist in Hash.
     */
    public Object get(Object key) throws InexistentEntryException{
        int hashCode = Math.abs(key.hashCode() % keys.length);
        for(int i = hashCode; i < keys.length; i++){
            if(keys[i] == null){
                throw new InexistentEntryException();
            }
            else if(keys[i].getKey().equals(EMPTY)){
            }
            else if(keys[i].getKey().equals(key)){
                Object ret = keys[i].getKey();
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
            return position < keys.length;
        }

        @Override
        public Object next() {
            if(keys[position] != null && !keys[position].equals(EMPTY)){
                return keys[position++];
            }
            else if(hasNext()){
                position++;
                next();
            }
            return null;
        }
    
    }
    
    public Object[] toArray(){
        LinkedList list = new LinkedList();
        for(int i = 0; i < keys.length; i++){
            if(keys[i] != null && !keys[i].equals(EMPTY)){
                list.add(keys[i].getKey());
            }
        }
        return list.toArray();
    }
    
    public List toList(){
        LinkedList list = new LinkedList();
        for(int i = 0; i < keys.length; i++){
            if(keys[i] != null && !keys[i].equals(EMPTY)){
                list.add(keys[i].getKey());
            }
        }
        return list;
    }
}
