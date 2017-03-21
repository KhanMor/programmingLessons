package models.springdata;

import models.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mordr on 21.03.2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmailAndPassword(String email, String Password);
    User findByEmail(String email);
}
