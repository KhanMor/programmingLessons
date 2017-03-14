package controllers.mvc;

import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.RoleService;
import services.UserRoleService;
import services.UserService;
import spring.security.SecurityUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для аутентификации
 */
@Controller
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);
   /* private static final String BLOCKED_USER_MESSAGE = "Пользователь заблокирован.";
    private static final String AUTH_FAIL_MESSAGE = "Введен не верный пароль или имя пользователя. Попробуйте еще раз.";*/
    /*private static final String AUTH_ATTRIBUTE_NAME = "user";
    private static final String ADMIN_ATTRIBUTE_NAME = "adminLoggedIn";
    private static final String ERROR_ATTRIBUTE_NAME = "error_message";
    private static final String USER_NAME_ATTRIBUTE_NAME = "user_name";*/
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

    /*
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
    }*/

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        //logger.trace("user " + session.getAttribute(AUTH_ATTRIBUTE_NAME) + " logged out.");
        //session.invalidate();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, authentication);
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            User user = securityUser.getUser();
            logger.trace("User " + user.getEmail() + " logged out");
        }
        return "redirect:/login";
    }
}
