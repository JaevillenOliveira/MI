
package Model;

public class Entry {
    
    private Object key;
    private Object value;
    
    
    /**
     * Contructor of the class.
     * @param key The key of the Entry.
     * @param value The value of the Entry.
     */
    public Entry(Object key, Object value){
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    
     /**
     * Method that overwrite the Object equals and compares the Key of the Entry.
     * @param obj The Entry to be compared.
     * @return True if the keys of the two Entries are equals.
     */
    @Override
    public boolean equals(Object obj){    
        Entry comp = (Entry)obj;
        if(key != null && comp.getKey() != null){
            return key.equals(comp.getKey());
        }
        if(key == null && comp.getKey() == null){
            return true;
        }
        return false;
    }
    
     /**
     * Method that overwrite the Object hashCode and create a own HashCode for the class.
     * @return A hashCode.
     */
    @Override
    public int hashCode(){
        String str = key.toString();
        int h = 0;
        
        for(int i = 0; i < str.length(); i++){
            h = 31 * h + str.charAt(i);
        }
        return h;
    }
    
    
    
    
    
}
