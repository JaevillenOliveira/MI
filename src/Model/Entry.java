
package Model;

public class Entry {
    
    private Object key;
    private Object value;
    
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
    @Override
    public boolean equals(Object obj){    
        Entry comp = (Entry)obj;
        if(key.equals(comp.getKey())){ 
            return true;
        }
        else{
            return false;
        }
    }
    
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
