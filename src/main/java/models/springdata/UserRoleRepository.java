package models.springdata;

import models.entity.Role;
import models.entity.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Mordr on 21.03.2017.
 */
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete from userrole where user_id = :user_id", nativeQuery = true)
    void deleteUserAllRoles(@Param("user_id") Integer user_id);
    @Modifying
    @Transactional
    @Query(value = "select * from userrole where user_id = :user_id", nativeQuery = true)
    List<UserRole> getUserAllRoles(@Param("user_id") Integer user_id);
    @Query(value = "select b.* from userrole a" +
            " inner join role b on a.role_id = b.id" +
            " where a.user_id = :user_id and a.role_id = :role_id limit 1", nativeQuery = true)
    Role findUserRole(@Param("user_id") Integer user_id, @Param("role_id") Integer role_id);
}
