package services.daoimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.entity.Course;
import models.entity.User;
import models.pojo.CoursePOJO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import services.CourseService;
import springconfig.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Реализация сервиса работы с курсами
 */
//@Service
@Deprecated
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = Logger.getLogger(CourseServiceImpl.class);
    private SuperDAO<Course> courseDAO;

    @Autowired
    public void setCourseDAO(SuperDAO<Course> courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public List<CoursePOJO> getCourses() throws ServiceException {
        try {
            List<Course> courses = courseDAO.list();
            List<CoursePOJO> coursePOJOS = new ArrayList<>(courses.size());
            for (Course course:courses) {
                CoursePOJO coursePOJO = new CoursePOJO(course);
                coursePOJOS.add(coursePOJO);
            }
            return coursePOJOS;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public CoursePOJO getCourse(Integer id) throws ServiceException {
        try {
            Course course = courseDAO.get(id);
            return new CoursePOJO(course);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createCourse(CoursePOJO coursePOJO) throws ServiceException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                logger.trace("new course was added by user ");
                SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
                User user = securityUser.getUser();
                Course course = new Course();
                course.setName(coursePOJO.getName());
                course.setDuration(coursePOJO.getDuration());
                course.setAuthor(user);
                courseDAO.insert(course);
            }
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCourse(CoursePOJO coursePOJO) throws ServiceException {
        try {
            Integer id = coursePOJO.getId();
            Course course = courseDAO.get(id);
            course.setName(coursePOJO.getName());
            course.setDuration(coursePOJO.getDuration());
            courseDAO.update(course);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCourse(Integer id) throws ServiceException {
        try {
            courseDAO.delete(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
