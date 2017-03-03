package services;

import common.exceptions.ServiceException;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;

import java.util.List;

/**
 * Created by Mordr on 27.02.2017.
 * Сервис для назначения пользователям ролей
 */
@SuppressWarnings("unused")
public interface UserRoleService {
    void createUserRole(UserRole userRole) throws ServiceException;
    void clearUserRoles(Integer id) throws ServiceException;
    List<UserRole> getUserRoles(User user) throws ServiceException;
    boolean checkIfUserHasRole(User user, Role role) throws ServiceException;
}
