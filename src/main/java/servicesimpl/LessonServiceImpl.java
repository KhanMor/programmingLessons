package servicesimpl;

import common.exceptions.DAOException;
import common.exceptions.ServiceException;
import models.dao.SuperDAO;
import models.pojo.Lesson;
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

    @Autowired
    public void setLessonDAO(SuperDAO<Lesson> lessonDAO) {
        this.lessonDAO = lessonDAO;
    }

    @Override
    public List<Lesson> getLessons() throws ServiceException {
        try {
            return lessonDAO.list();
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
    public void createLesson(Lesson lesson) throws ServiceException {
        try {
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
