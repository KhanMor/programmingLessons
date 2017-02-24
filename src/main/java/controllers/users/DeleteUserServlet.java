package controllers.users;

import exceptions.DAOException;
import models.dao.SuperDAO;
import models.daoimpl.UserDAOImpl;
import models.pojo.User;
import org.apache.log4j.Logger;

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
            SuperDAO<User> userDAO = new UserDAOImpl();
            Integer id = Integer.parseInt(req.getParameter("id"));
            User user = userDAO.get(id);
            userDAO.delete(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
