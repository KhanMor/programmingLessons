package services;

import common.exceptions.ServiceException;
import models.pojo.RolePOJO;
import models.pojo.UserPOJO;
import models.pojo.UserRolePOJO;

import java.util.List;

/**
 * Created by Mordr on 27.02.2017.
 * Сервис для назначения пользователям ролей
 */
public interface UserRoleService {
    void createUserRole(UserRolePOJO userRolePOJO) throws ServiceException;
    void clearUserRoles(Integer id) throws ServiceException;
    List<UserRolePOJO> getUserRoles(UserPOJO userPOJO) throws ServiceException;
    boolean checkIfUserHasRole(UserPOJO userPOJO, RolePOJO rolePOJO) throws ServiceException;
}
