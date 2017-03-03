package servicesimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.dao.UserAuthorizationDAO;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.UserService;

import java.util.List;

/**
 * Created by Mordr on 01.03.2017.
 * Реализация сервиса работы с пользователями
 */
@SuppressWarnings("unused")
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserRoleServiceImpl.class);
    private UserAuthorizationDAO userAuthorizationDAO;
    private SuperDAO<User> userDAO;

    @Autowired
    public void setUserAuthorizationDAO(UserAuthorizationDAO userAuthorizationDAO) {
        this.userAuthorizationDAO = userAuthorizationDAO;
    }
    @Autowired
    public void setUserDAO(SuperDAO<User> userDAO) {
        this.userDAO = userDAO;
    }

    public User loginUser(String email, String password) throws ServiceException {
        try {
            return userAuthorizationDAO.findUserByEmailAndPassword(email, password);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public List<User> getUsers() throws ServiceException {
        try {
            return userDAO.list();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void createUser(User user) throws ServiceException {
        try {
            userDAO.insert(user);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public User getUser(Integer id) throws ServiceException {
        try {
            return userDAO.get(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void updateUser(User user) throws ServiceException {
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void deleteUser(Integer user_id) throws ServiceException {
        try {
            userDAO.delete(user_id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        try {
            return userAuthorizationDAO.findUserByEmail(email);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
