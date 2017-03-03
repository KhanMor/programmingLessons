package services;

import common.exceptions.ServiceException;
import models.pojo.Role;

import java.util.List;

/**
 * Created by Mordr on 27.02.2017.
 * Cервис работы с ролями
 */
@SuppressWarnings("unused")
public interface RoleService {
    Role createRoleIfNotFound(String roleName) throws ServiceException;
    List<Role> getRoles() throws ServiceException;
}
