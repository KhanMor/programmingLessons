package services;

import common.exceptions.DAOException;
import models.dao.SuperDAO;
import models.daoimpl.UserAuthorizationDAO;
import models.daoimpl.UserRoleDAOImpl;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;

import java.util.List;

/**
 * Created by Mordr on 27.02.2017.
 */
public class UserRoleService {
    private static final SuperDAO<UserRole> USER_ROLE_DAO = new UserRoleDAOImpl();
    private static final UserAuthorizationDAO USER_AUTHORIZATION_DAO = new UserAuthorizationDAO();

    public static void createUserRole(UserRole userRole) throws DAOException {
        USER_ROLE_DAO.insert(userRole);
    }

    public static void clearUserRoles(Integer id) throws DAOException {
        USER_AUTHORIZATION_DAO.deleteUserAllRoles(id);
    }

    public static List<UserRole> getUserRoles(User user) throws DAOException {
        return USER_AUTHORIZATION_DAO.getUserAllRoles(user);
    }

    public static boolean checkIfUserHasRole(User user, String roleName) throws DAOException {
        List<UserRole> userRoles = getUserRoles(user);
        for(UserRole userRole:userRoles) {
            Role role =userRole.getRole();
            if(role.getRole().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
