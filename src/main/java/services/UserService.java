package services;

import common.exceptions.ServiceException;
import models.pojo.UserPOJO;

import java.sql.Date;
import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 * Сервис работы с пользователями
 */
@SuppressWarnings("unused")
public interface UserService {
    UserPOJO loginUser(String email, String password) throws ServiceException;
    List<UserPOJO> getUsers() throws ServiceException;
    UserPOJO createUser(String firstName, String surname, String patronymic, Date birthday, String sex,
                    String email, String password, String roleName) throws ServiceException;
    UserPOJO getUser(Integer id) throws ServiceException;
    UserPOJO updateUser(Integer id, String firstName, String surname, String patronymic,
                        Date birthday, String sex, String email, String password, String roleName, Boolean changePassword) throws ServiceException;
    UserPOJO updateUserProfile(Integer id, String firstName, String surname,
                               String patronymic, Date birthday, String sex) throws ServiceException;
    void deleteUser(Integer user_id) throws ServiceException;
    UserPOJO getUserByEmail(String email) throws ServiceException;
}
