package controllers.servlets.courses;

import common.exceptions.ServiceException;
import controllers.servlets.SuperServlet;
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
 * Удаление курса
 */
@Deprecated
@WebServlet(urlPatterns = "/courses/delete")
public class DeleteCourseServlet extends SuperServlet {
    private static final Logger logger = Logger.getLogger(DeleteCourseServlet.class);
    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            courseService.deleteCourse(id);
        } catch (ServiceException e) {
            logger.error(e);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
