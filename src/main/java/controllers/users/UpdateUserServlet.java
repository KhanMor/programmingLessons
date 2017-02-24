package controllers.users;

import crypt.EncryptMD5;
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
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Mordr on 24.02.2017.
 */
@WebServlet(urlPatterns = "/usersAdmin/update")
public class UpdateUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UsersAdminServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            req.setCharacterEncoding("UTF-8");
            Integer id = Integer.parseInt(req.getParameter("id"));
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            password = EncryptMD5.encrypt(password);
            String firstname = req.getParameter("firstname");
            String surname = req.getParameter("surname");
            String patronymic = req.getParameter("patronymic");
            java.util.Date date = dateFormat.parse(req.getParameter("birthday"));
            Date birthday = new Date(date.getTime());
            String sex = req.getParameter("sex");
            Boolean changePassword = Boolean.parseBoolean(req.getParameter("changePassword"));

            SuperDAO<User> userDAO = new UserDAOImpl();
            User user = userDAO.get(id);
            user.setEmail(email);
            if(changePassword) {
                user.setPassword(password);
            }
            user.setFirstName(firstname);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            user.setBirthday(birthday);
            user.setSex(sex);
            userDAO.update(user);

            PrintWriter out = resp.getWriter();
            out.print(user.getId());
            out.flush();
        } catch (ParseException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        } catch (DAOException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
