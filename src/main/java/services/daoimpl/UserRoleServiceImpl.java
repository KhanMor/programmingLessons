package services.daoimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.dao.UserAuthorizationDAO;
import models.entity.Role;
import models.entity.UserRole;
import models.pojo.RolePOJO;
import models.pojo.UserPOJO;
import models.pojo.UserRolePOJO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.UserRoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 01.03.2017.
 * Реализация сервиса работы с ролями пользователя
 */
//@Service
@Deprecated
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
    public void createUserRole(UserRolePOJO userRolePOJO) throws ServiceException {
        try {
            UserRole userRole = new UserRole(userRolePOJO);
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
    public List<UserRolePOJO> getUserRoles(UserPOJO userPOJO) throws ServiceException {
        try {
            Integer user_id = userPOJO.getId();
            List<UserRole> userRoles = userAuthorizationDAO.getUserAllRoles(user_id);
            List<UserRolePOJO> userRolePOJOS = new ArrayList<>(userRoles.size());
            for (UserRole userRole:userRoles) {
                UserRolePOJO userRolePOJO = new UserRolePOJO(userRole);
                userRolePOJOS.add(userRolePOJO);
            }
            return userRolePOJOS;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkIfUserHasRole(UserPOJO userPOJO, RolePOJO rolePOJO) throws ServiceException {
        try {
            Integer user_id = userPOJO.getId();
            Integer role_id = rolePOJO.getId();
            Role findedRole = userAuthorizationDAO.findUserRole(user_id, role_id);
            return findedRole != null;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
