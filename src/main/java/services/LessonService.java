package services;

import common.exceptions.ServiceException;
import models.pojo.Lesson;

import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Сервис работы с уроками
 */
@SuppressWarnings("unused")
public interface LessonService {
    List<Lesson> getLessons() throws ServiceException;
    Lesson getLesson(Integer id) throws ServiceException;
    void createLesson(Lesson lesson) throws ServiceException;
    void updateLesson(Lesson lesson) throws ServiceException;
    void deleteLesson(Integer id) throws ServiceException;
}
