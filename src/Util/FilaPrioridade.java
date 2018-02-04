/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.*;

/**
 *
 * @author Jaevillen
 */
public class FilaPrioridade implements IPriorityQueue{
    private Comparable[] data;
    private int size;

    /**
     * Constructor of the class.
     * 
     * @param tamanhoInicial The Initial lenght of the queue.
     */
    public FilaPrioridade(int tamanhoInicial){
        data = new Comparable[tamanhoInicial];
    }

    /**
     * Method that verifies if the queue is empty.
     * @return Tru if the queue is empty.
     */
    @Override
    public boolean isEmpty() {
       return this.size == 0;
    }

    /**
     * Method that verifies the size of the queue.
     * @return
     */
    @Override
    public int size() {//Retorna a quantidade de objetos da fila
        return size;
    }
        
    private void swap(int parent, int child){//Troca as posições dos objetos do array
        Comparable temp = data[parent];//Variável temporária recebe o maior 
        data [parent] = data[child];//O 'filho' passa para a atual posição do 'pai'
        data[child] = temp;//O 'pai' que estava na variável temporária passa para a antiga posição do 'filho'
    }
    
    /**
     *Method that add a Comparable in the queue. 
     * 
     * @param obj The Comparable.
     */
    @Override
    public void add(Comparable obj) {
         if(size == data.length){//verifica se o array está no limite de armazenamento
           Comparable[] temp = new Comparable[this.size*2];//Cria um novo array temporário com tamnho dobrado
           System.arraycopy(data, 0, temp, 0, this.size);//Copia os dados do primeiro array no array temporário
           data = temp;//Recebe o array temporário  
        }
        data[size] = obj;//Insere novo objeto no fim da fila
        int parent = (this.size - 1)/2;//Recebe o índice do 'pai' do novo objeto
        int child = this.size;//Recebe a atual posição do novo objeto
        while(child > 0 && data[parent].compareTo(data[child]) > 0){//Verifica se o 'pai' é maior que o 'filho'
            swap(parent, child);//Troca as posições
            child = parent;//O 'filho' passa a ser o 'pai'
            parent = (parent - 1)/2;//Encontra o novo 'pai' do filho
        }
        size++;//Incrementa varivel de quantidade de objetos
    }

    /**
     * Method that takes the first element of the queue.
     * @return The smaller element of the queue.
     */
    @Override
    public Comparable peek() {//Devolve a informação do primeiro objeto da fila
        if(this.isEmpty()){//Verifica se a fila está vazia
            return null;
        }
        return data[0];//Retorna o objeto da primeira posição (maior)
    }

    /**
     * Method that compares which is the smaller of two elements.
     * @param i The first element
     * @param j The second element.
     * @return The smaller element.
     */
    public int min(int i, int j){//Método para descobrir qual dos dois 'filhos' de um 'pai' é o maior
        if(i < size && j < size){
            return data[i].compareTo(data[j]) < 0 ? i : j;//Compara os objetos e retorna o maior
        }
        else if(i < size){
            return i;//Retorna o maior
        }
        else if(j < size){
            return j;//Retorna o maior
        }
        return data.length;
    }

    /**
     * Method that removes the element that is in the beginning of the queue.
     * @return The element removed.
     */
    @Override
    public Comparable remove() {//Remove maior objeto da fila
        Comparable ret = data[0];//Variável temporária recebe maior objeto da fila
        data[0] = data[size - 1];//O último objeto passa a ser o primeiro da fila
        size--;//A variável de quantidade é decrementada
        
        //Reorganiza o array para volte a ser uma heap
        int parent = 0;//Inicializa sendo o primeiro objeto
        int child = min(parent * 2 + 1, parent * 2 + 2);//Recebe maior 'filho'
        while(child < size && data[child].compareTo(data[parent]) < 0){//Verifica se o 'pai' é menor que o 'filho'
            swap(parent, child);//Troca as posições
            parent = child;//Recebe o atual maior 'filho'
            child = min(parent * 2 + 1, parent * 2 + 2);//Recebe maior 'filho'
        }
        return ret;//Retorna o objeto removido
    }
    
    /**
     * Method that returns an Iterator of the class.
     * @return One Iterator of the class.
     */
    public Iterator iterator(){//Devolve um iterador pra fila
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator{
        private int cur;
        
        @Override
        public boolean hasNext() {//Verifica se algo na próxima posição da fila
            return data[cur] != null;
        }

        @Override
        public Comparable next() {//Devolve próximo objeto da fila
            Comparable ret = data[cur];//Armazena objeto que será retornado
            cur++;
            return ret;
        }
    }
}
