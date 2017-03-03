package services;

import common.exceptions.ServiceException;
import models.pojo.User;

import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 * Сервис работы с пользователями
 */
@SuppressWarnings("unused")
public interface UserService {
    User loginUser(String email, String password) throws ServiceException;
    List<User> getUsers() throws ServiceException;
    void createUser(User user) throws ServiceException;
    User getUser(Integer id) throws ServiceException;
    void updateUser(User user) throws ServiceException;
    void deleteUser(Integer user_id) throws ServiceException;
    User getUserByEmail(String email) throws ServiceException;
}
