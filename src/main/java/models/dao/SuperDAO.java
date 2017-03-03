package models.dao;

import common.exceptions.DAOException;

import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 * Общие, CRUD операции с данными
 */
public interface SuperDAO<T> {
    List<T> list() throws DAOException;
    T get(Integer id)  throws DAOException;
    void insert(T t) throws DAOException;
    void update(T t) throws DAOException;
    void delete(Integer id) throws DAOException;
    void deleteAll() throws DAOException;
}
