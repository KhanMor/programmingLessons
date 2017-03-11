package servicesimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.CourseLessonsDAO;
import models.dao.SuperDAO;
import models.pojo.Course;
import models.pojo.Lesson;
import models.pojo.mini.MiniPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.LessonService;

import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Реализация сервиса работы с уроками
 */
@Service
public class LessonServiceImpl implements LessonService{
    private static final Logger logger = Logger.getLogger(LessonServiceImpl.class);
    private SuperDAO<Lesson> lessonDAO;
    private SuperDAO<Course> courseDAO;
    private CourseLessonsDAO courseLessonsDAO;

    @Autowired
    public void setLessonDAO(SuperDAO<Lesson> lessonDAO) {
        this.lessonDAO = lessonDAO;
    }
    @Autowired
    public void setCourseDAO(SuperDAO<Course> courseDAO) {
        this.courseDAO = courseDAO;
    }
    @Autowired
    public void setCourseLessonsDAO(CourseLessonsDAO courseLessonsDAO) {
        this.courseLessonsDAO = courseLessonsDAO;
    }

    @Override
    public List<Lesson> getAllLessons() throws ServiceException {
        try {
            return lessonDAO.list();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Lesson> getCourseLessons(Integer course_id) throws ServiceException {
        try {
            return courseLessonsDAO.courseLessonsList(course_id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MiniPojo> getCourseLessonsMini(Integer course_id) throws ServiceException {
        try {
            return courseLessonsDAO.courseLessonsListMini(course_id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Lesson getLesson(Integer id) throws ServiceException {
        try {
            return lessonDAO.get(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createLesson(Integer course_id, Integer orderNum, String theme, Double duration, String content) throws ServiceException {
        try {
            Course course = courseDAO.get(course_id);
            Lesson lesson = new Lesson();
            lesson.setCourse(course);
            lesson.setOrderNum(orderNum);
            lesson.setTheme(theme);
            lesson.setDuration(duration);
            lesson.setContent(content);
            lessonDAO.insert(lesson);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateLesson(Lesson lesson) throws ServiceException {
        try {
            lessonDAO.update(lesson);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteLesson(Integer id) throws ServiceException {
        try {
            lessonDAO.delete(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
