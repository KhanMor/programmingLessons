package services.daoimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.dao.UserAuthorizationDAO;
import models.entity.Role;
import models.entity.User;
import models.entity.UserRole;
import models.pojo.UserPOJO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import services.UserService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 01.03.2017.
 * Реализация сервиса работы с пользователями
 */
//@Service
@Deprecated
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserRoleServiceImpl.class);
    private UserAuthorizationDAO userAuthorizationDAO;
    private SuperDAO<User> userDAO;
    private PasswordEncoder passwordEncoder;
    private SuperDAO<Role> roleDAO;

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
    @Autowired
    public void setRoleDAO(SuperDAO<Role> roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public UserPOJO loginUser(String email, String password) throws ServiceException {
        try {
            User user = userAuthorizationDAO.findValidUser(email, password);
            if (user != null) {
                return new UserPOJO(user);
            } else {
                return  null;
            }
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
    @Override
    public List<UserPOJO> getUsers() throws ServiceException {
        try {
            List<User> users = userDAO.list();
            List<UserPOJO> userPOJOS = new ArrayList<>(users.size());
            for (User user:users) {
                UserPOJO userPOJO = new UserPOJO(user);
                userPOJOS.add(userPOJO);
            }
            return userPOJOS;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
    @Override
    public UserPOJO createUser(String firstName, String surname, String patronymic, Date birthday, String sex,
                           String email, String password, String roleName) throws ServiceException {
        try {
            password = passwordEncoder.encode(password);
            User user = new User(firstName, surname, patronymic, birthday, sex, email, password);
            Role role = userAuthorizationDAO.findRoleByName(roleName);
            if(role == null) {
                role = new Role();
                role.setRole(roleName);
                roleDAO.insert(role);
            }
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            List<UserRole> userRoles = new ArrayList<>(1);
            userRoles.add(userRole);
            user.setUserRoles(userRoles);
            userDAO.insert(user);
            return new UserPOJO(user);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
    @Override
    public UserPOJO getUser(Integer id) throws ServiceException {
        try {
            User user = userDAO.get(id);
            return new UserPOJO(user);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
    @Override
    public UserPOJO updateUser(Integer id, String firstName, String surname, String patronymic,
                           Date birthday, String sex, String email, String password, String roleName, Boolean changePassword) throws ServiceException {
        try {
            User user = userDAO.get(id);
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
            Role role = userAuthorizationDAO.findRoleByName(roleName);
            if(role == null) {
                role = new Role();
                role.setRole(roleName);
                roleDAO.insert(role);
            }
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            List<UserRole> userRoles = new ArrayList<>(1);
            userRoles.add(userRole);
            user.setUserRoles(userRoles);
            userDAO.update(user);
            return new UserPOJO(user);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserPOJO updateUserProfile(Integer id, String firstName, String surname, String patronymic, Date birthday, String sex) throws ServiceException {
        try {
            User user = userDAO.get(id);
            user.setFirstName(firstName);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            user.setBirthday(birthday);
            user.setSex(sex);
            userDAO.update(user);
            return new UserPOJO(user);
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
    public UserPOJO getUserByEmail(String email) throws ServiceException {
        try {
            User user = userAuthorizationDAO.findUserByEmail(email);
            return new UserPOJO(user);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
