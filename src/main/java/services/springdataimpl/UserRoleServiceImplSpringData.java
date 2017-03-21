package services.springdataimpl;

import common.exceptions.ServiceException;
import models.entity.Role;
import models.entity.UserRole;
import models.pojo.RolePOJO;
import models.pojo.UserPOJO;
import models.pojo.UserRolePOJO;
import models.springdata.UserRoleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.UserRoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 21.03.2017.
 * Сервис для работы с ролями пользователей, использующий репозиторий Spring Data
 */
@Service
public class UserRoleServiceImplSpringData implements UserRoleService {
    private static final Logger logger = Logger.getLogger(UserRoleServiceImplSpringData.class);
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImplSpringData(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void createUserRole(UserRolePOJO userRolePOJO) throws ServiceException {
        UserRole userRole = new UserRole(userRolePOJO);
        userRoleRepository.save(userRole);
    }

    @Override
    public void clearUserRoles(Integer id) throws ServiceException {
        userRoleRepository.deleteUserAllRoles(id);
    }

    @Override
    public List<UserRolePOJO> getUserRoles(UserPOJO userPOJO) throws ServiceException {
        Integer user_id = userPOJO.getId();
        List<UserRole> userRoles = userRoleRepository.getUserAllRoles(user_id);
        List<UserRolePOJO> userRolePOJOS = new ArrayList<>(userRoles.size());
        for (UserRole userRole:userRoles) {
            UserRolePOJO userRolePOJO = new UserRolePOJO(userRole);
            userRolePOJOS.add(userRolePOJO);
        }
        return userRolePOJOS;
    }

    @Override
    public boolean checkIfUserHasRole(UserPOJO userPOJO, RolePOJO rolePOJO) throws ServiceException {
        Integer user_id = userPOJO.getId();
        Integer role_id = rolePOJO.getId();
        Role findedRole = userRoleRepository.findUserRole(user_id, role_id);
        return findedRole != null;
    }
}
