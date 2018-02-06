
package Util;

import Model.*;
import Exceptions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


public class Graph implements IGraph{
    
    private int numVertex;
    private int numEdges;
    private HashSet vertex;
    private HashSet edges;
    

    public Graph(){
        vertex = new HashSet();  
        edges = new HashSet();
    }


    @Override
    public void addVertex(Object key) throws DuplicateEntryException{
        Vertex newVertex = new Vertex(key);
        vertex.put(newVertex);
        numVertex++;
    }


    @Override
    public Iterator vertex() {
        return vertex.iterator();         
    }

    @Override
    public int numVertex() {
        return numVertex;
    }
    

    @Override
    public void removeVertex(Object key) throws EmptyHashException, InexistentEntryException, DontHaveAdjacenciesException{
        Vertex verify = (Vertex)vertex.get(key);
        
        if(verify.getAdjacency() == null){
            throw new DontHaveAdjacenciesException();
        }
        
        HashMap verifyMap = verify.getAdjacency();
        
        Entry[] entries = verifyMap.toArray();
        
        for(int i = 0; i < verifyMap.size(); i++){
            if(entries[i] == null){
            
            }
            else if(entries[i].getKey() == null && entries[i].getValue() == null){
                
            } 
            else{
                Vertex point = (Vertex)entries[i].getKey();
                HashMap map = point.getAdjacency();
                
                map.remove(verify);
            }
        }    
    }


    @Override
    public void addEdge(Object vertexA, Object vertexB, Object data) throws DuplicateEntryException, AlreadyHasAdjacency, InexistentVertexException, LoopIsNotAllowedException{
        
        Vertex Avertex = new Vertex(vertexA);
        Vertex Bvertex = new Vertex(vertexB);
        
        Vertex verify = null;
        Vertex verifyOther = null;
        
        try{
            verify = (Vertex)vertex.get(Avertex);
        
            verifyOther = (Vertex) vertex.get(Bvertex);
        }
        catch(InexistentEntryException iex){
            throw new InexistentVertexException();
        }
        
        Edge edge = new Edge(verify, verifyOther, data);
        
        if(Avertex.equals(Bvertex)){
            throw new LoopIsNotAllowedException();
        }
        try{
            vertex.contains(Avertex);
            vertex.contains(Bvertex);
        }
        catch(InexistentEntryException iex){
            throw new InexistentVertexException();
        }
        
        try{
            verify.getAdjacency().contains(verifyOther);
            throw new AlreadyHasAdjacency();
        }
        catch(InexistentEntryException iex){
            verify.getAdjacency().put(verifyOther, edge);
        }
        
        try{
            verifyOther.getAdjacency().contains(verify);
            throw new AlreadyHasAdjacency();
        }
        catch(InexistentEntryException iex){
            verifyOther.getAdjacency().put(verify, edge);   
        }
        try{
            edges.contains(edge);
            throw new DuplicateEntryException();
        }
        catch(InexistentEntryException iex){
            edges.put(edge);
        }
    }


    @Override
    public Edge getEdge(Object vertexA, Object vertexB) throws InexistentEntryException{
        Vertex a = new Vertex(vertexA);
        Vertex b = new Vertex(vertexB);
        Edge edge = new Edge(a, b, null);
        
        if(edges.contains(edge)){
            Edge ret = (Edge)edges.get(edge);
            return ret;
        }
        throw new InexistentEntryException();
    }
    

    public Object getKey(Object key) throws InexistentEntryException{
       return ((Vertex) this.getVertex(key)).getVertex();  
    }
  
    public Object getVertex(Object key) throws InexistentEntryException{
        Vertex searchVertex = new Vertex(key);
        
        Vertex ret = (Vertex) vertex.get(searchVertex);
        
       return ret;  
    }

    @Override
    public Iterator edges() {
        return edges.iterator();
    }

    @Override
    public int numEdges() {
        return numEdges;
    }

    @Override
    public void removeEdge(Edge removed) throws EmptyHashException, InexistentEntryException{
        Vertex oneVertex = removed.getA();
        Vertex otherVertex = removed.getB();
        
        oneVertex.getAdjacency().remove(otherVertex);
        
        otherVertex.getAdjacency().remove(oneVertex);
    }
    

    public HashMap shortestPath(Object obj) throws InexistentEntryException, DuplicateEntryException{
        
        HashMap shortestPath = new HashMap();
        FilaPrioridade queue = new FilaPrioridade(10);
        
        Vertex v = (Vertex) this.getVertex(obj);
        queue.add(new EntryDjikstra(v, null, 0));
        
        while(!queue.isEmpty()){
            
            EntryDjikstra e = (EntryDjikstra) queue.remove();
            v = (Vertex) e.getCur();
            
            try{
                if(!shortestPath.contains(v)){
                    shortestPath.put(v, e);
                }
            }catch(InexistentEntryException ex){
                shortestPath.put(v, e);
            }
            
            Iterator adjacencies = v.getAdjacency().iterator();
            while(adjacencies.hasNext()){
                
                Entry vertEdge = (Entry) adjacencies.next();
                if(vertEdge != null){
                    Vertex u = (Vertex) vertEdge.getKey();

                    double dist = (double) ((Edge) vertEdge.getValue()).getWeight();
                    dist = dist + e.getDistance();
                   
                    try{
                        EntryDjikstra t = (EntryDjikstra) ((Entry) shortestPath.get(u)).getValue();
                        if(dist < t.getDistance()){
                            queue.add(new EntryDjikstra(u, v, dist));
                        }
                    }catch(InexistentEntryException ex){
                        queue.add(new EntryDjikstra(u, v, dist));
                    }
                }
            } 
        }
        return shortestPath;  
    }
    
    public Stack <EntryDjikstra> path (Object start, Object end, HashMap paths) throws InexistentEntryException{
        
        Stack <EntryDjikstra> stack = new Stack();
        
        Vertex s = (Vertex) this.getVertex(start);
        Vertex e = (Vertex) this.getVertex(end);
        
        while(!s.equals(e)){
            stack.push((EntryDjikstra) ((Entry) paths.get(e)).getValue());
            e = stack.peek().getPrev();
        }
        stack.push((EntryDjikstra) ((Entry) paths.get(e)).getValue());
        return stack;
    } 

    @Override
    public Iterator outGoingEdges(Object vertex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public int outDegree(Object vertex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
    @Override
    public Iterator incomingEdges(Object vertex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    @Override
    public int inDegree(Object vertex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public LinkedList getAllVertex(){
        return vertex.toList();
    }
    
    public LinkedList getAllEdges(){
        return edges.toList();
    }
    
}
