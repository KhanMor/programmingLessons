package controllers.servlets.courses;

import common.exceptions.ServiceException;
import controllers.servlets.SuperServlet;
import models.pojo.Course;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import services.CourseService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Mordr on 03.03.2017.
 * Сервлет добавления нового курса
 */
@Deprecated
//@WebServlet(urlPatterns = "/courses/add")
public class AddCourseServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(AddCourseServlet.class);
    private static final String AUTH_ATTRIBUTE_NAME = "user";
    private CourseService courseService;
    private UserService userService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String email = (String) session.getAttribute(AUTH_ATTRIBUTE_NAME);
        try {
            User author = userService.getUserByEmail(email);
            String name = req.getParameter("name");
            Double duration = Double.parseDouble(req.getParameter("duration"));

            Course course = new Course();
            course.setAuthor(author);
            course.setName(name);
            course.setDuration(duration);

            courseService.createCourse(course);
            PrintWriter out = resp.getWriter();
            out.print(course.getId());
            out.flush();
        } catch (ServiceException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
