package services.springdataimpl;

import common.exceptions.ServiceException;
import models.entity.Role;
import models.entity.User;
import models.entity.UserRole;
import models.pojo.UserPOJO;
import models.springdata.RoleRepository;
import models.springdata.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import services.UserService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 21.03.2017.
 * Сервис для работы с пользователями, использующий репозиторий Spring Data
 */
@Service
public class UserServiceImplSpringData implements UserService{
    private static final Logger logger = Logger.getLogger(UserServiceImplSpringData.class);
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImplSpringData(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserPOJO loginUser(String email, String password) throws ServiceException {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            return new UserPOJO(user);
        } else {
            return  null;
        }
    }

    @Override
    public List<UserPOJO> getUsers() throws ServiceException {
        List<User> users = (List<User>) userRepository.findAll();
        List<UserPOJO> userPOJOS = new ArrayList<>(users.size());
        for (User user:users) {
            UserPOJO userPOJO = new UserPOJO(user);
            userPOJOS.add(userPOJO);
        }
        return userPOJOS;
    }

    @Override
    public UserPOJO createUser(String firstName, String surname, String patronymic, Date birthday, String sex, String email, String password, String roleName) throws ServiceException {
        password = passwordEncoder.encode(password);
        User user = new User(firstName, surname, patronymic, birthday, sex, email, password);
        Role role = roleRepository.findByRole(roleName);
        if(role == null) {
            role = new Role();
            role.setRole(roleName);
            roleRepository.save(role);
        }
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        List<UserRole> userRoles = new ArrayList<>(1);
        userRoles.add(userRole);
        user.setUserRoles(userRoles);
        userRepository.save(user);
        return new UserPOJO(user);
    }

    @Override
    public UserPOJO getUser(Integer id) throws ServiceException {
        User user = userRepository.findOne(id);
        return new UserPOJO(user);
    }

    @Override
    public UserPOJO updateUser(Integer id, String firstName, String surname, String patronymic, Date birthday, String sex, String email, String password, String roleName, Boolean changePassword) throws ServiceException {
        User user = userRepository.findOne(id);
        password = passwordEncoder.encode(password);
        user.setEmail(email);
        if(changePassword) {
            user.setPassword(password);
        }
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setBirthday(birthday);
        user.setSex(sex);
        Role role = roleRepository.findByRole(roleName);
        if(role == null) {
            role = new Role();
            role.setRole(roleName);
            roleRepository.save(role);
        }
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        List<UserRole> userRoles = new ArrayList<>(1);
        userRoles.add(userRole);
        user.setUserRoles(userRoles);
        userRepository.save(user);
        return new UserPOJO(user);
    }

    @Override
    public UserPOJO updateUserProfile(Integer id, String firstName, String surname, String patronymic, Date birthday, String sex) throws ServiceException {
        User user = userRepository.findOne(id);
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setBirthday(birthday);
        user.setSex(sex);
        userRepository.save(user);
        return new UserPOJO(user);
    }

    @Override
    public void deleteUser(Integer user_id) throws ServiceException {
        userRepository.delete(user_id);
    }

    @Override
    public UserPOJO getUserByEmail(String email) throws ServiceException {
        User user = userRepository.findByEmail(email);
        return new UserPOJO(user);
    }
}
