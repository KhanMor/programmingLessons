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
import org.springframework.stereotype.Service;
import services.UserRoleService;

import java.util.List;

/**
 * Created by Mordr on 01.03.2017.
 * Реализация сервиса работы с ролями пользователя
 */
@SuppressWarnings("unused")
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private static final Logger logger = Logger.getLogger(UserRoleServiceImpl.class);
    private SuperDAO<UserRole> userRoleDAO;
    private UserAuthorizationDAO userAuthorizationDAO;

    @Autowired
    public void setUserRoleDAO(SuperDAO<UserRole> userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }
    @Autowired
    public void setUserAuthorizationDAO(UserAuthorizationDAO userAuthorizationDAO) {
        this.userAuthorizationDAO = userAuthorizationDAO;
    }

    @Override
    public void createUserRole(UserRole userRole) throws ServiceException {
        try {
            userRoleDAO.insert(userRole);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void clearUserRoles(Integer id) throws ServiceException {
        try {
            userAuthorizationDAO.deleteUserAllRoles(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserRole> getUserRoles(User user) throws ServiceException {
        try {
            return userAuthorizationDAO.getUserAllRoles(user);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkIfUserHasRole(User user, Role role) throws ServiceException {
        try {
            Role findedRole = userAuthorizationDAO.findUserRole(user, role);
            return findedRole != null;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
