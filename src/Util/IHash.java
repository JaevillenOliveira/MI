
package Util;

public interface IHash {
    
    public void put(Object key, Object value);
    
    public Object get(Object key);
    
    public void removeKey(Object key);
    
    public void removeValue(Object value);
    
    public boolean isEmpty();
    
    public int size(); 
}
