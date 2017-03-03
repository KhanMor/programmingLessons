package controllers.servlets.courses;

import common.exceptions.ServiceException;
import controllers.servlets.SuperServlet;
import models.pojo.Course;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import services.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Сервлет вывода списка курсов
 */
@WebServlet(urlPatterns = "/courses")
public class CoursesListServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(CoursesListServlet.class);
    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Course> courses = courseService.getCourses();
            req.setAttribute("courses", courses);
            req.getRequestDispatcher("/courses.jsp").forward(req, resp);
        } catch (ServiceException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
