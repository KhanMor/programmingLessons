package controllers.servlets.login;

import common.exceptions.ServiceException;
import controllers.servlets.SuperServlet;
import crypt.EncryptMD5;
import models.dao.UserAuthorizationDAO;
import models.pojo.Role;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import services.RoleService;
import services.UserRoleService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mordr on 22.02.2017.
 * Вход пользователя в систему
 */
//@WebServlet(urlPatterns = "/login", loadOnStartup = 1)
public class LoginServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAO.class);
    private static final String BLOCKED_USER_MESSAGE = "Пользователь заблокирован.";
    private static final String AUTH_FAIL_MESSAGE = "Введен не верный пароль или имя пользователя. Попробуйте еще раз.";
    private static final String AUTH_ATTRIBUTE_NAME = "user";
    private static final String ADMIN_ATTRIBUTE_NAME = "adminLoggedIn";
    private static final String ERROR_ATTRIBUTE_NAME = "error_message";
    private static final String GREETING_ATTRIBUTE_NAME = "greeting_message";

    private UserService userService;
    private UserRoleService userRoleService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            password = EncryptMD5.encrypt(password);
            User user = userService.loginUser(email, password);
            logger.trace(user);
            if(user != null) {
                Role blocked_role = roleService.createRoleIfNotFound("blocked");
                boolean isBlocked = userRoleService.checkIfUserHasRole(user, blocked_role);
                if(isBlocked) {
                    logger.trace("user with email " + email + " authorization attempt, user is blocked");
                    req.setAttribute(ERROR_ATTRIBUTE_NAME, BLOCKED_USER_MESSAGE);
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                } else {

                    HttpSession session = req.getSession();
                    session.setMaxInactiveInterval(30 * 60);
                    session.setAttribute(AUTH_ATTRIBUTE_NAME, user.getEmail());

                    Role admin_role = roleService.createRoleIfNotFound("admin");
                    boolean isAdmin = userRoleService.checkIfUserHasRole(user, admin_role);
                    if (isAdmin) {
                        session.setAttribute(ADMIN_ATTRIBUTE_NAME, user.getEmail());
                    }
                    req.setAttribute(GREETING_ATTRIBUTE_NAME, user.getFirstName());
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            } else {
                logger.trace("user with email " + email + " authorization failed");
                req.setAttribute(ERROR_ATTRIBUTE_NAME, AUTH_FAIL_MESSAGE);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (ServiceException | NoSuchAlgorithmException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
