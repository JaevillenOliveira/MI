
package Util;

import java.util.Iterator;

/**
 *
 * @author AlmirNeto
 */
public class Queue implements IQueue{
    
    private Node head; 
    private Node tail; 
    private int size; 

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(Object o) {
        Node node = new Node(o);
        if(isEmpty()){
            head = node;
            tail = node;
            size++;
        }
        else{
            tail.setNext(node);
            tail = node;
        }
    }

    @Override
    public Object poll() {
        Object ret = null;
        if(isEmpty()){
            return null;
        }
        else{
            ret = head.getData();
            head = head.getNext();
        }
        return ret;
    }

    @Override
    public Object peek() {
        if(!isEmpty()){
            return head;
        }
        return null;
    }

    public boolean contains(Object obj){
        if(!isEmpty()){
            Node search = head;
            while(search != null){
                if(search.getData().equals(obj)){
                    return true;
                }
                search = search.getNext();
            }
        }
        return false; 
    }
    
    public Iterator iterator(){
        return new MyIt();
    }
    
    private class MyIt implements Iterator{
        
        private Node actual = head;
        
        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public Object next() {
            Object ret = actual.getData();
            actual = actual.getNext();
            return ret;
        }   
    }

    
    
    private class Node{

        private Object data; 
        private Node next; 

    Node(Object novo){
        this.data = novo;
        this.next = null;
    }
        public Object getData() {
            return data; 
        }

        public void setData(Object data) {
            this.data = data; 
        }

        public Node getNext() {
            return next; 
        }

        public void setNext(Node next) {
            this.next = next; 
        }
    
    }
}
