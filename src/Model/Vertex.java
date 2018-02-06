/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Util.HashMap;

/**
 *
 * @author AlmirNeto
 */
public class Vertex {
    
    private Object vertex;
    private HashMap adjacency;
    
    public Vertex(Object vertex){
        this.vertex = vertex;
        this.adjacency = new HashMap();
    }

    public Object getVertex() {
        return vertex;
    }

    public void setVertex(Object vertex) {
        this.vertex = vertex;
    }

    public HashMap getAdjacency() {
        return adjacency;
    }

    public void setAdjacency(HashMap adjacency) {
        this.adjacency = adjacency;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Vertex){
            Vertex vertexObj = (Vertex)obj;
            if(this.vertex.equals(vertexObj.getVertex())){
                return true;
            }
            return false;
        }
        return false;
    }
    
    @Override
    public int hashCode(){
        return vertex.hashCode();
    }
}
