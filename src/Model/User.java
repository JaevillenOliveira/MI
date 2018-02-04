
package Model;

import Util.*;

/**
 *
 * @author Jaevillen
 */
public class User implements Comparable{
    
    private String password;;
    private String cpf;
    private String name;
    private String email;
    private Queue trip;
    
    /**
     * Contructor of the class.
     * @param password The password for the login.
     * @param name The Name of the User.
     * @param cpf The CPF of the User.
     * @param email The email of the User.
     */
    public User(String password, String name, String cpf, String email){
        this.password = password;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.trip = new Queue();
    }
    
    /**
     * Contructor of the class.
     * @param cpf The CPF of the User.
     * @param password The password for the login.
     */
    public User(String cpf, String password){
        this.cpf = cpf;
        this.password = password;
    }
    
    /**
     * Contructor of the class.
     * @param cpf The CPF of the User.
     */
    public User(String cpf){
        this.cpf = cpf;
    }
            
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Queue getTrip() {
        return trip;
    }

    public void setTrip(Queue trip) {
        this.trip = trip;
    }
        
    /**
     * Method that overwrite the Object equals and compares the CPF of the User.
     * @param obj The User to be compared.
     * @return True if the CPFs of the two Users are equals.
     */
    @Override
    public boolean equals(Object obj){
        User comp = (User)obj;
        
        return this.cpf.equalsIgnoreCase(comp.getCpf());
    }
    
    /*
     * Method that overwrite the Object hashCode and create a own HashCode for the class.
     * @return A hashCode.
     */
    @Override
    public int hashCode(){
        int h = 0;
        String newString = cpf.concat(password.concat(name.concat(email)));
        for(int i = 0; i < newString.length(); i++){
            h = 31 * newString.charAt(i) + i;
        }
        return h;
    }

    @Override
    public int compareTo(Object t) {
        User o = (User) t;
        return this.cpf.compareTo(o.getCpf());  
        
    }

    
}
