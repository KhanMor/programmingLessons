package controllers;

import crypt.EncryptMD5;
import common.exceptions.DAOException;
import models.daoimpl.UserAuthorizationDAO;
import models.pojo.User;
import org.apache.log4j.Logger;
import services.UserRoleService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Mordr on 22.02.2017.
 */
@WebServlet(urlPatterns = "/login", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAO.class);
    private static final String AUTH_FAIL_MESSAGE = "Введен не верный пароль или имя пользователя. Попробуйте еще раз.";
    private static final String AUTH_ATTRIBUTE_NAME = "user";
    private static final String ADMIN_ATTRIBUTE_NAME = "adminLoggedIn";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        password = EncryptMD5.encrypt(password);
        try {
            User user = UserService.loginUser(email, password);
            logger.trace(user);
            if(user != null) {
                HttpSession session = req.getSession();
                session.setMaxInactiveInterval(30*60);
                session.setAttribute(AUTH_ATTRIBUTE_NAME, user.getEmail());

                boolean isAdmin = UserRoleService.checkIfUserHasRole(user, "admin");
                if(isAdmin) {
                    session.setAttribute(ADMIN_ATTRIBUTE_NAME, user.getEmail());
                }

                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                logger.trace("user with email " + email + " authorization failed");
                req.setAttribute("error_message", AUTH_FAIL_MESSAGE);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (DAOException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
