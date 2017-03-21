package services.daoimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.CourseLessonsDAO;
import models.dao.SuperDAO;
import models.entity.Course;
import models.entity.Lesson;
import models.pojo.LessonPOJO;
import models.pojo.mini.MiniPOJO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.LessonService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Реализация сервиса работы с уроками
 */
//@Service
@Deprecated
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
    public List<LessonPOJO> getAllLessons() throws ServiceException {
        try {
            List<Lesson> lessons = lessonDAO.list();
            List<LessonPOJO> lessonPOJOS = new ArrayList<>(lessons.size());
            for (Lesson lesson:lessons) {
                LessonPOJO lessonPOJO = new LessonPOJO(lesson);
                lessonPOJOS.add(lessonPOJO);
            }
            return lessonPOJOS;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<LessonPOJO> getCourseLessons(Integer course_id) throws ServiceException {
        try {
            List<Lesson> lessons = courseLessonsDAO.courseLessonsList(course_id);
            List<LessonPOJO> lessonPOJOS = new ArrayList<>(lessons.size());
            for (Lesson lesson:lessons) {
                LessonPOJO lessonPOJO = new LessonPOJO(lesson);
                lessonPOJOS.add(lessonPOJO);
            }
            return lessonPOJOS;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    private String formatLesson(Lesson lesson) {
        return lesson.getOrderNum() + ". " + lesson.getTheme() + " - " + lesson.getDuration();
    }
    @Override
    public List<MiniPOJO> getCourseLessonsMini(Integer course_id) throws ServiceException {
        try {
            List<Lesson> lessons = courseLessonsDAO.courseLessonsList(course_id);
            List<MiniPOJO> miniPOJOS = new ArrayList<>(lessons.size());
            for (Lesson lesson:lessons) {
                MiniPOJO miniPOJO = new MiniPOJO();
                miniPOJO.setId(lesson.getId());
                miniPOJO.setName(formatLesson(lesson));
                miniPOJOS.add(miniPOJO);
            }
            return miniPOJOS;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public LessonPOJO getLesson(Integer id) throws ServiceException {
        try {
            Lesson lesson = lessonDAO.get(id);
            return new LessonPOJO(lesson);
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
    public void updateLesson(LessonPOJO lessonPOJO) throws ServiceException {
        try {
            Integer id = lessonPOJO.getId();
            Lesson lesson = lessonDAO.get(id);
            lesson.setOrderNum(lessonPOJO.getOrderNum());
            lesson.setTheme(lessonPOJO.getTheme());
            lesson.setDuration(lessonPOJO.getDuration());
            lesson.setContent(lessonPOJO.getContent());
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
