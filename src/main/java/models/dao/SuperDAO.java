package models.dao;

import exceptions.DAOException;

import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 */
public interface SuperDAO<T> {
    List<T> list() throws DAOException;
    T get(Integer id)  throws DAOException;
    void insert(T t) throws DAOException;
    void update(T t) throws DAOException;
    void delete(T t) throws DAOException;
    void deleteAll() throws DAOException;
}
