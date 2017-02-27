package controllers.users;

import common.exceptions.DAOException;
import org.apache.log4j.Logger;
import services.UserRoleService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mordr on 24.02.2017.
 */
@WebServlet(urlPatterns = "/usersAdmin/delete")
public class DeleteUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UsersAdminServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            UserRoleService.clearUserRoles(id);
            UserService.deleteUser(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
