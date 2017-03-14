package controllers.servlets.users;

import common.exceptions.ServiceException;
import controllers.servlets.SuperServlet;
import crypt.EncryptMD5;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import services.RoleService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 24.02.2017.
 * Редактирование пользователя
 */
@Deprecated
//@WebServlet(urlPatterns = "/usersAdmin/update")
public class UpdateUserServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(UsersAdminServlet.class);

    private RoleService roleService;
    private UserService userService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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

            Role role = roleService.createRoleIfNotFound(roleName);

            userService.updateUser(id, firstname, surname, patronymic, birthday, sex, email, password, role, changePassword);
            //userRoleService.clearUserRoles(id);
            //userRoleService.createUserRole(userRole);
        } catch (ParseException | ServiceException | NoSuchAlgorithmException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
