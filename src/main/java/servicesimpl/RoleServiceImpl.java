package servicesimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.dao.UserAuthorizationDAO;
import models.pojo.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.RoleService;

import java.util.List;

/**
 * Created by Mordr on 01.03.2017.
 * Реализвация сервиса работы с ролями
 */
@SuppressWarnings("unused")
@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);
    private SuperDAO<Role> roleDAO;
    private UserAuthorizationDAO userAuthorizationDAO;

    @Autowired
    public void setRoleDAO(SuperDAO<Role> roleDAO) {
        this.roleDAO = roleDAO;
    }
    @Autowired
    public void setUserAuthorizationDAO(UserAuthorizationDAO userAuthorizationDAO) {
        this.userAuthorizationDAO = userAuthorizationDAO;
    }

    @Override
    public Role createRoleIfNotFound(String roleName) throws ServiceException {
        try {
            Role role = userAuthorizationDAO.findRoleByName(roleName);
            if(role == null) {
                role = new Role();
                role.setRole(roleName);
                roleDAO.insert(role);
            }
            return role;
        } catch (DAOException e) {
            logger.trace(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Role> getRoles() throws ServiceException {
        try {
            return roleDAO.list();
        } catch (DAOException e) {
            logger.trace(e);
            throw new ServiceException(e);
        }
    }
}
