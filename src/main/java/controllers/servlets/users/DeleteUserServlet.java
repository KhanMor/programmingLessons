package controllers.servlets.users;

import common.exceptions.ServiceException;
import controllers.servlets.SuperServlet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mordr on 24.02.2017.
 * Удаление пользователя
 */
@WebServlet(urlPatterns = "/usersAdmin/delete")
public class DeleteUserServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(UsersAdminServlet.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            //userRoleService.clearUserRoles(id);
            userService.deleteUser(id);
        } catch (ServiceException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
