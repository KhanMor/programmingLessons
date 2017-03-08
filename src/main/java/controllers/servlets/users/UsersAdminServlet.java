package controllers.servlets.users;

import common.exceptions.ServiceException;
import controllers.servlets.SuperServlet;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import services.UserRoleService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 * Панель управления пользователями
 */
//@WebServlet(urlPatterns = "/usersAdmin")
public class UsersAdminServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(UsersAdminServlet.class);

    private UserService userService;
    private UserRoleService userRoleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = userService.getUsers();
            for(User user:users) {
                List<UserRole> userRoles = userRoleService.getUserRoles(user);
                user.setUserRoles(userRoles);
            }
            req.setAttribute("users", users);
            req.getRequestDispatcher("/usersAdmin.jsp").forward(req, resp);
        } catch (ServiceException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
