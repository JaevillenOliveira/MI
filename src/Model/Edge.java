/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author AlmirNeto
 */
public class Edge {
    
    private Vertex A;
    private Vertex B;
    private Object weight;
    
    /**
     * The constructor of the class.
     * @param a The first Vertex.
     * @param b The second Vertex.
     * @param weight The weight of the Edge.
     */
    public Edge(Vertex a, Vertex b, Object weight){
        this.A = a;
        this.B = b;
        this.weight = weight;
    }

    public Vertex getA() {
        return A;
    }

    public void setA(Vertex A) {
        this.A = A;
    }

    public Vertex getB() {
        return B;
    }

    public void setB(Vertex B) {
        this.B = B;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }
    
    @Override
    public boolean equals(Object obj){
        Edge ed = (Edge)obj;
        if((ed.getA().equals(A) || ed.getA().equals(B)) && (ed.getB().equals(A) || ed.getB().equals(B))){
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode(){
        String value = weight.toString();
        int h = 0;
            for (int i=0; i < value.length(); i++)
                h = 31 * h + value.charAt(i);
        return h;
    }
}
