package dao;

import models.Role;
import models.User;
import models.UserRole;

import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
public interface UserRoleDAO {
    void addUserRole(User user, Role role);
    void removeUserRole(User user, Role role);
    void deleteAll();
    Role getRoleByName(String rolename);
}
