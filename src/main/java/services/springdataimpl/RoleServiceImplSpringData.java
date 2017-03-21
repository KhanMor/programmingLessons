package services.springdataimpl;

import common.exceptions.ServiceException;
import models.entity.Role;
import models.pojo.RolePOJO;
import models.springdata.RoleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 21.03.2017.
 * Сервис для работы с ролями, использующий репозиторий Spring Data
 */
@Service
public class RoleServiceImplSpringData implements RoleService {
    private static final Logger logger = Logger.getLogger(RoleServiceImplSpringData.class);
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImplSpringData(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RolePOJO createRoleIfNotFound(String roleName) throws ServiceException {
        Role role = roleRepository.findByRole(roleName);
        if(role == null) {
            role = new Role();
            role.setRole(roleName);
            roleRepository.save(role);
        }
        return new RolePOJO(role);
    }

    @Override
    public List<RolePOJO> getRoles() throws ServiceException {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        List<RolePOJO> rolePOJOS = new ArrayList<>(roles.size());
        for (Role role:roles) {
            RolePOJO rolePOJO = new RolePOJO(role);
            rolePOJOS.add(rolePOJO);
        }
        return rolePOJOS;
    }
}
