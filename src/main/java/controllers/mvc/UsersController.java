package controllers.mvc;

import common.exceptions.ServiceException;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.RoleService;
import services.UserRoleService;
import services.UserService;
import spring.security.SecurityUser;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для редактирования данных пользователей
 */
@Controller
public class UsersController {
    private static final Logger logger = Logger.getLogger(UsersController.class);
    private static final String REGISTRATION_ROLE_NAME = "student";
    private static final String PRE_POST_ADMIN_ROLE = "hasRole('ROLE_admin')";
    private RoleService roleService;
    private UserService userService;
    private UserRoleService userRoleService;

    @Autowired
    public UsersController(RoleService roleService, UserService userService, UserRoleService userRoleService) {
        this.roleService = roleService;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @RequestMapping(value = "/usersAdmin", method = RequestMethod.GET)
    @PreAuthorize(PRE_POST_ADMIN_ROLE)
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
    @PreAuthorize(PRE_POST_ADMIN_ROLE)
    public @ResponseBody Integer addUser(@RequestParam String email, @RequestParam String password, @RequestParam String firstname,
                                         @RequestParam String surname, @RequestParam String patronymic,
                                         @RequestParam String birthday, @RequestParam String sex,
                                         @RequestParam String role) throws ParseException, ServiceException, NoSuchAlgorithmException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = dateFormat.parse(birthday);
        Date birthdayD = new Date(date.getTime());
        Role roleE = roleService.createRoleIfNotFound(role);
        User user = userService.createUser(firstname, surname, patronymic, birthdayD, sex, email, password, roleE);
        logger.trace("new user created with email " + email);
        return user.getId();
    }
    @RequestMapping(value = "/usersAdmin/update", method = RequestMethod.POST)
    @PreAuthorize(PRE_POST_ADMIN_ROLE)
    public @ResponseBody Integer updateUser(@RequestParam Integer id,
                                            @RequestParam String email, @RequestParam String password, @RequestParam String firstname,
                                            @RequestParam String surname, @RequestParam String patronymic,
                                            @RequestParam String birthday, @RequestParam String sex,
                                            @RequestParam Boolean changePassword, @RequestParam String role) throws ParseException, ServiceException, NoSuchAlgorithmException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = dateFormat.parse(birthday);
        Date birthdayD = new Date(date.getTime());
        Role roleE = roleService.createRoleIfNotFound(role);
        userRoleService.clearUserRoles(id);
        userService.updateUser(id, firstname, surname, patronymic, birthdayD, sex, email, password, roleE, changePassword);
        logger.trace("user with email " + email + " was updated");
        return id;
    }
    @RequestMapping(value = "/usersAdmin/delete", method = RequestMethod.POST)
    @PreAuthorize(PRE_POST_ADMIN_ROLE)
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
        Role studentRole = roleService.createRoleIfNotFound(REGISTRATION_ROLE_NAME);
        userService.createUser(firstname, surname, patronymic, birthdayD, sex, email, password, studentRole);
        logger.trace("new user (student) with email " + email + " was registered");
        return "registration.success";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String viewUserProfile(Model model) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        model.addAttribute(user);
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(@RequestParam Integer id, @RequestParam String firstname,
                                @RequestParam String surname, @RequestParam String patronymic,
                                @RequestParam String birthday, @RequestParam String sex) throws ParseException, ServiceException, NoSuchAlgorithmException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = dateFormat.parse(birthday);
        Date birthdayD = new Date(date.getTime());
        User user = userService.updateUserProfile(id, firstname, surname, patronymic, birthdayD, sex);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        User userSecurity = securityUser.getUser();
        userSecurity.setFirstName(firstname);
        userSecurity.setSurname(surname);
        userSecurity.setPatronymic(patronymic);
        userSecurity.setBirthday(birthdayD);
        userSecurity.setSex(sex);

        logger.trace("user with email " + user.getEmail() + " updated his profile");
        return "redirect:/profile";
    }
}
