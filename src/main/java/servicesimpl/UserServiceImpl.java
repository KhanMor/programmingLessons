package servicesimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.dao.UserAuthorizationDAO;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import services.UserService;

import java.sql.Date;
import java.util.ArrayList;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserAuthorizationDAO(UserAuthorizationDAO userAuthorizationDAO) {
        this.userAuthorizationDAO = userAuthorizationDAO;
    }
    @Autowired
    public void setUserDAO(SuperDAO<User> userDAO) {
        this.userDAO = userDAO;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loginUser(String email, String password) throws ServiceException {
        try {
            return userAuthorizationDAO.findUserByEmailAndPassword(email, password);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
    @Override
    public List<User> getUsers() throws ServiceException {
        try {
            return userDAO.list();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
    @Override
    public User createUser(String firstName, String surname, String patronymic, Date birthday, String sex,
                           String email, String password, Role role) throws ServiceException {
        try {
            password = passwordEncoder.encode(password);
            User user = new User(firstName, surname, patronymic, birthday, sex, email, password);

            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            List<UserRole> userRoles = new ArrayList<>(1);
            userRoles.add(userRole);
            user.setUserRoles(userRoles);
            userDAO.insert(user);
            return user;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
    @Override
    public User getUser(Integer id) throws ServiceException {
        try {
            return userDAO.get(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
    @Override
    public User updateUser(Integer id, String firstName, String surname, String patronymic,
                           Date birthday, String sex, String email, String password, Role role, Boolean changePassword) throws ServiceException {
        try {
            User user = getUser(id);
            password = passwordEncoder.encode(password);
            user.setEmail(email);
            if(changePassword) {
                user.setPassword(password);
            }
            user.setFirstName(firstName);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            user.setBirthday(birthday);
            user.setSex(sex);

            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            List<UserRole> userRoles = new ArrayList<>(1);
            userRoles.add(userRole);

            user.setUserRoles(userRoles);

            userDAO.update(user);
            return user;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User updateUserProfile(Integer id, String firstName, String surname, String patronymic, Date birthday, String sex) throws ServiceException {
        try {
            User user = getUser(id);
            user.setFirstName(firstName);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            user.setBirthday(birthday);
            user.setSex(sex);
            userDAO.update(user);
            return user;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
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
