package org.example.model.daos;


import org.example.model.exceptions.DAOException;

import java.util.List;

public interface DAO<T> {

    List<T> getAll() throws DAOException;

    void insertar(T obj) throws DAOException;

    void borrar(T obj) throws DAOException;

    //Tots els mètodes necessaris per interactuar en la BD

}
