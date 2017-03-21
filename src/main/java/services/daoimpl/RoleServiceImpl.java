package services.daoimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.dao.UserAuthorizationDAO;
import models.entity.Role;
import models.pojo.RolePOJO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 01.03.2017.
 * Реализвация сервиса работы с ролями
 */
//@Service
@Deprecated
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
    public RolePOJO createRoleIfNotFound(String roleName) throws ServiceException {
        try {
            Role role = userAuthorizationDAO.findRoleByName(roleName);
            if(role == null) {
                role = new Role();
                role.setRole(roleName);
                roleDAO.insert(role);
            }
            return new RolePOJO(role);
        } catch (DAOException e) {
            logger.trace(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<RolePOJO> getRoles() throws ServiceException {
        try {
            List<Role> roles = roleDAO.list();
            List<RolePOJO> rolePOJOS = new ArrayList<>(roles.size());
            for (Role role:roles) {
                RolePOJO rolePOJO = new RolePOJO(role);
                rolePOJOS.add(rolePOJO);
            }
            return rolePOJOS;
        } catch (DAOException e) {
            logger.trace(e);
            throw new ServiceException(e);
        }
    }
}
