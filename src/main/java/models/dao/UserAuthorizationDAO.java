package models.dao;

import common.exceptions.DAOException;
import models.entity.Role;
import models.entity.User;
import models.entity.UserRole;

import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 * Набор методов для авторизация пользователя
 */
public interface UserAuthorizationDAO {
    User findValidUser(String email, String password) throws DAOException;
    Role findRoleByName(String roleName) throws DAOException;
    void deleteUserAllRoles(Integer user_id) throws DAOException;
    List<UserRole> getUserAllRoles(Integer user_id) throws DAOException;
    Role findUserRole(Integer user_id, Integer role_id) throws DAOException;
    User findUserByEmail(String email) throws DAOException;
}
