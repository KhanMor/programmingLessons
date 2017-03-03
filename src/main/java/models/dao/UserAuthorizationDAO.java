package models.dao;

import common.exceptions.DAOException;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 * Набор методов для авторизация пользователя
 */
@Repository
public interface UserAuthorizationDAO {
    User findUserByEmailAndPassword(String email, String password) throws DAOException;
    Role findRoleByName(String roleName) throws DAOException;
    void deleteUserAllRoles(Integer user_id) throws DAOException;
    List<UserRole> getUserAllRoles(User user) throws DAOException;
    Role findUserRole(User user, Role role) throws DAOException;
    User findUserByEmail(String email) throws DAOException;
}
