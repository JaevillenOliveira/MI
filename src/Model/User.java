
package Model;

import Util.Queue;

public class User implements Comparable{
    
    private String password;;
    private String cpf;
    private String name;
    private String email;
    private Queue trip;
    
    public User(String password, String name, String cpf, String email){
        this.password = password;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }
    
    public User(String cpf, String password){
        this.cpf = cpf;
        this.password = password;
    }
    
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
        
    @Override
    public boolean equals(Object obj){
        User comp = (User)obj;
        
        if(this.cpf.equalsIgnoreCase(comp.getCpf())){
            return true;
        }
        else{
            return false;
        }
    }
    
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
    public int compareTo(Object obj) {
        if(obj instanceof User){
            User user = (User )obj;
            if(cpf.compareTo(user.getCpf()) < 0){
                return -1;
            }
            else if(cpf.compareTo(user.getCpf()) > 0){
                return 1;
            }
            else{
                return 0;
            }     
        }
        // Throw Exception if the Object isn't an User.
        throw new RuntimeException();
    }
    
}
