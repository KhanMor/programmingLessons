package dao;

import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 */
public interface SuperDAO<T> {
    List<T> list();
    T get(Integer id);
    void insert(T t);
    void update(T t);
    void delete(T t);
    void deleteAll();
}
