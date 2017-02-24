package controllers.users;

import crypt.EncryptMD5;
import exceptions.DAOException;
import models.dao.SuperDAO;
import models.daoimpl.UserDAOImpl;
import models.pojo.User;
import org.apache.log4j.Logger;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 */
@WebServlet(urlPatterns = "/usersAdmin")
public class UsersAdminServlet extends HttpServlet{
    private static final Logger logger = Logger.getLogger(UsersAdminServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users= UserService.getUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/usersAdmin.jsp").forward(req, resp);
        } catch (DAOException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
