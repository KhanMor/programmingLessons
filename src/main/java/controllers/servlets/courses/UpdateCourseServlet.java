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

/**
 * Created by Mordr on 03.03.2017.
 * Редактированик курса
 */
@Deprecated
@WebServlet(urlPatterns = "/courses/update")
public class UpdateCourseServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(UpdateCourseServlet.class);
    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            Double duration = Double.parseDouble(req.getParameter("duration"));

            Course course = courseService.getCourse(id);
            course.setName(name);
            course.setDuration(duration);

            courseService.updateCourse(course);
        } catch (ServiceException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
