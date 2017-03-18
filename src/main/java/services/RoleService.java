package services;

import common.exceptions.ServiceException;
import models.pojo.RolePOJO;

import java.util.List;

/**
 * Created by Mordr on 27.02.2017.
 * Cервис работы с ролями
 */
@SuppressWarnings("unused")
public interface RoleService {
    RolePOJO createRoleIfNotFound(String roleName) throws ServiceException;
    List<RolePOJO> getRoles() throws ServiceException;
}
