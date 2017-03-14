package services;

import common.exceptions.ServiceException;
import models.pojo.Role;
import models.pojo.User;

import java.sql.Date;
import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 * Сервис работы с пользователями
 */
@SuppressWarnings("unused")
public interface UserService {
    User loginUser(String email, String password) throws ServiceException;
    List<User> getUsers() throws ServiceException;
    User createUser(String firstName, String surname, String patronymic, Date birthday, String sex,
                    String email, String password, Role role) throws ServiceException;
    User getUser(Integer id) throws ServiceException;
    User updateUser(Integer id, String firstName, String surname, String patronymic,
                    Date birthday, String sex, String email, String password, Role role, Boolean changePassword) throws ServiceException;
    User updateUserProfile(Integer id, String firstName, String surname, String patronymic, Date birthday, String sex) throws ServiceException;
    void deleteUser(Integer user_id) throws ServiceException;
    User getUserByEmail(String email) throws ServiceException;
}
