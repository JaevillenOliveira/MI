package Util;

import Exceptions.*;

public interface ITree extends Iterable{
    
    public Object buscar(Comparable item) throws NotFoundException;

    public void inserir(Comparable item) throws DuplicatedDataException;

    public void remover(Comparable item) throws NotFoundException;

}