package controllers;

import crypt.EncryptMD5;
import common.exceptions.DAOException;
import models.daoimpl.UserAuthorizationDAO;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;
import services.RoleService;
import services.UserRoleService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 */
@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet{
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAO.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            req.setCharacterEncoding("UTF-8");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            password = EncryptMD5.encrypt(password);
            String firstname = req.getParameter("firstname");
            String surname = req.getParameter("surname");
            String patronymic = req.getParameter("patronymic");
            java.util.Date date = dateFormat.parse(req.getParameter("birthday"));
            Date birthday = new Date(date.getTime());
            String sex = req.getParameter("sex");

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstname);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            user.setBirthday(birthday);
            user.setSex(sex);

            Role studentRole = RoleService.createRoleIfNotFound("student");
            UserRole studentUserRole = new UserRole();
            studentUserRole.setRole(studentRole);
            studentUserRole.setUser(user);
            List<UserRole> userRoles = new ArrayList<>(1);
            userRoles.add(studentUserRole);

            user.setUserRoles(userRoles);

            UserService.createUser(user);
            UserRoleService.createUserRole(studentUserRole);
            resp.sendRedirect(req.getContextPath() + "/registrationSuccess");
        } catch (ParseException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        } catch (DAOException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
