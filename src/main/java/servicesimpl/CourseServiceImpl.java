package servicesimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.pojo.Course;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.CourseService;

import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Реализация сервиса работы с курсами
 */
@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = Logger.getLogger(CourseServiceImpl.class);
    private SuperDAO<Course> courseDAO;

    @Autowired
    public void setCourseDAO(SuperDAO<Course> courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public List<Course> getCourses() throws ServiceException {
        try {
            return courseDAO.list();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Course getCourse(Integer id) throws ServiceException {
        try {
            return courseDAO.get(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createCourse(Course course) throws ServiceException {
        try {
            courseDAO.insert(course);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCourse(Course course) throws ServiceException {
        try {
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
