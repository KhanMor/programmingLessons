package controllers.mvc;

import common.exceptions.ServiceException;
import crypt.EncryptMD5;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.RoleService;
import services.UserRoleService;
import services.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для редактирования данных пользователей
 */
@Controller
public class UsersController {
    private static final Logger logger = Logger.getLogger(UsersController.class);
    private static final String ERROR_ATTRIBUTE_NAME = "error_message";
    private static final String REGISTRATION_ROLE_NAME = "student";
    private RoleService roleService;
    private UserService userService;
    private UserRoleService userRoleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @RequestMapping(value = "/usersAdmin", method = RequestMethod.GET)
    public String listUsers(Model model) throws ServiceException {
        List<User> users = userService.getUsers();
        for(User user:users) {
            List<UserRole> userRoles = userRoleService.getUserRoles(user);
            user.setUserRoles(userRoles);
        }
        model.addAttribute("users", users);
        return "usersAdmin";
    }
    @RequestMapping(value = "/usersAdmin/add", method = RequestMethod.POST)
    public @ResponseBody Integer addUser(@RequestParam String email, @RequestParam String password, @RequestParam String firstname,
                                         @RequestParam String surname, @RequestParam String patronymic,
                                         @RequestParam String birthday, @RequestParam String sex,
                                         @RequestParam String role) throws ParseException, ServiceException, NoSuchAlgorithmException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = dateFormat.parse(birthday);
        Date birthdayD = new Date(date.getTime());
        password = EncryptMD5.encrypt(password);

        User user = new User(firstname, surname, patronymic, birthdayD, sex, email, password);

        Role roleE = roleService.createRoleIfNotFound(role);
        UserRole userRole = new UserRole();
        userRole.setRole(roleE);
        userRole.setUser(user);
        List<UserRole> userRoles = new ArrayList<>(1);
        userRoles.add(userRole);

        user.setUserRoles(userRoles);

        userService.createUser(user);
        return user.getId();
    }
    @RequestMapping(value = "/usersAdmin/update", method = RequestMethod.POST)
    public @ResponseBody Integer updateUser(@RequestParam Integer id,
                                            @RequestParam String email, @RequestParam String password, @RequestParam String firstname,
                                            @RequestParam String surname, @RequestParam String patronymic,
                                            @RequestParam String birthday, @RequestParam String sex,
                                            @RequestParam Boolean changePassword, @RequestParam String role) throws ParseException, ServiceException, NoSuchAlgorithmException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = dateFormat.parse(birthday);
        Date birthdayD = new Date(date.getTime());
        password = EncryptMD5.encrypt(password);

        User user = userService.getUser(id);
        user.setEmail(email);
        if(changePassword) {
            user.setPassword(password);
        }
        user.setFirstName(firstname);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setBirthday(birthdayD);
        user.setSex(sex);

        Role roleE = roleService.createRoleIfNotFound(role);
        UserRole userRole = new UserRole();
        userRole.setRole(roleE);
        userRole.setUser(user);
        List<UserRole> userRoles = new ArrayList<>(1);
        userRoles.add(userRole);

        user.setUserRoles(userRoles);
        userRoleService.clearUserRoles(user.getId());
        userService.updateUser(user);
        return user.getId();
    }
    @RequestMapping(value = "/usersAdmin/delete", method = RequestMethod.POST)
    public @ResponseBody void deleteUser(@RequestParam Integer id) throws ServiceException {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistrationPage() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String doRegistration(@RequestParam String email, @RequestParam String password, @RequestParam String firstname,
                                 @RequestParam String surname, @RequestParam String patronymic,
                                 @RequestParam String birthday, @RequestParam String sex) throws ParseException, ServiceException, NoSuchAlgorithmException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date date = dateFormat.parse(birthday);
        Date birthdayD = new Date(date.getTime());
        password = EncryptMD5.encrypt(password);

        User user = new User(firstname, surname, patronymic, birthdayD, sex, email, password);

        Role studentRole = roleService.createRoleIfNotFound(REGISTRATION_ROLE_NAME);
        UserRole studentUserRole = new UserRole();
        studentUserRole.setRole(studentRole);
        studentUserRole.setUser(user);
        List<UserRole> userRoles = new ArrayList<>(1);
        userRoles.add(studentUserRole);

        user.setUserRoles(userRoles);

        userService.createUser(user);
        return "registration.success";
    }

}
