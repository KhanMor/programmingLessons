package models.springdata;

import models.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mordr on 21.03.2017.
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByRole(String role);
}
