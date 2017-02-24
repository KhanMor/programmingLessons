package services;

import exceptions.DAOException;
import models.dao.SuperDAO;
import models.daoimpl.UserAuthorizationDAO;
import models.daoimpl.UserDAOImpl;
import models.pojo.User;

import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 */
public class UserService {
    private static final UserAuthorizationDAO USER_AUTHORIZATION_DAO = new UserAuthorizationDAO();
    private static final SuperDAO<User> USER_DAO = new UserDAOImpl();

    public static User loginUser(String email, String password) throws DAOException {
        User user = USER_AUTHORIZATION_DAO.findUserByEmailAndPassword(email, password);
        return user;
    }

    public static List<User> getUsers() throws DAOException {
        List<User> users = USER_DAO.list();
        return users;
    }

    public static void addUser(User user) throws DAOException {
        USER_DAO.insert(user);
    }

    public static User getUser(Integer id) throws DAOException {
        User user = USER_DAO.get(id);
        return user;
    }

    public static void updateUser(User user) throws DAOException {
        USER_DAO.update(user);
    }

    public static void deleteUser(User user) throws DAOException {
        USER_DAO.delete(user);
    }

}
