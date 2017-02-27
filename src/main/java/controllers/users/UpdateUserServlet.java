package controllers.users;

import crypt.EncryptMD5;
import common.exceptions.DAOException;
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
            String roleName = req.getParameter("role");
            Boolean changePassword = Boolean.parseBoolean(req.getParameter("changePassword"));

            User user = UserService.getUser(id);
            user.setEmail(email);
            if(changePassword) {
                user.setPassword(password);
            }
            user.setFirstName(firstname);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            user.setBirthday(birthday);
            user.setSex(sex);

            Role role = RoleService.createRoleIfNotFound(roleName);
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            List<UserRole> userRoles = new ArrayList<>(1);
            userRoles.add(userRole);

            user.setUserRoles(userRoles);

            UserService.updateUser(user);
            UserRoleService.clearUserRoles(id);
            UserRoleService.createUserRole(userRole);
        } catch (ParseException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        } catch (DAOException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
