package controllers.mvc;

import common.exceptions.ServiceException;
import crypt.EncryptMD5;
import models.pojo.Role;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import services.RoleService;
import services.UserRoleService;
import services.UserService;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для аутентификации
 */
@Controller
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    private static final String BLOCKED_USER_MESSAGE = "Пользователь заблокирован.";
    private static final String AUTH_FAIL_MESSAGE = "Введен не верный пароль или имя пользователя. Попробуйте еще раз.";
    private static final String AUTH_ATTRIBUTE_NAME = "user";
    private static final String ADMIN_ATTRIBUTE_NAME = "adminLoggedIn";
    private static final String ERROR_ATTRIBUTE_NAME = "error_message";
    private static final String USER_NAME_ATTRIBUTE_NAME = "user_name";
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) throws ServiceException, NoSuchAlgorithmException {
        password = EncryptMD5.encrypt(password);
        User user = userService.loginUser(email, password);
        logger.trace(user);
        if(user != null) {
            Role blocked_role = roleService.createRoleIfNotFound("blocked");
            boolean isBlocked = userRoleService.checkIfUserHasRole(user, blocked_role);
            if(isBlocked) {
                logger.trace("user with email " + email + " authorization attempt, user is blocked");
                model.addAttribute(ERROR_ATTRIBUTE_NAME, BLOCKED_USER_MESSAGE);
                return "error";
            } else {
                session.setMaxInactiveInterval(30 * 60);
                session.setAttribute(AUTH_ATTRIBUTE_NAME, user.getEmail());
                session.setAttribute(USER_NAME_ATTRIBUTE_NAME, user.getFirstName());

                Role admin_role = roleService.createRoleIfNotFound("admin");
                boolean isAdmin = userRoleService.checkIfUserHasRole(user, admin_role);
                if (isAdmin) {
                    session.setAttribute(ADMIN_ATTRIBUTE_NAME, user.getEmail());
                }
                return "redirect:/index";
            }
        } else {
            logger.trace("user with email " + email + " authorization failed");
            model.addAttribute(ERROR_ATTRIBUTE_NAME, AUTH_FAIL_MESSAGE);
            return "login";
        }
    }
    final long Byte=2;
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doLogout(HttpSession session) {
        logger.trace("user " + session.getAttribute(AUTH_ATTRIBUTE_NAME) + " logged out.");
        session.invalidate();
        return "redirect:/login";
    }
}
