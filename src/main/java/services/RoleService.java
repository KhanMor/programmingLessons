package services;

import common.exceptions.DAOException;
import models.dao.SuperDAO;
import models.daoimpl.RoleDAOImpl;
import models.daoimpl.UserAuthorizationDAO;
import models.pojo.Role;

import java.util.List;

/**
 * Created by Mordr on 27.02.2017.
 */
public class RoleService {
    private static final SuperDAO<Role> ROLE_DAO = new RoleDAOImpl();
    private static final UserAuthorizationDAO USER_AUTHORIZATION_DAO = new UserAuthorizationDAO();

    public static Role createRoleIfNotFound(String roleName) throws DAOException {
        Role role = USER_AUTHORIZATION_DAO.findRoleByName(roleName);
        if(role == null) {
            role = new Role();
            role.setRole(roleName);
            ROLE_DAO.insert(role);
        }
        return role;
    }

    public static List<Role> getRoles() throws DAOException {
        return ROLE_DAO.list();
    }
}
