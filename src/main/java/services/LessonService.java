package services;

import common.exceptions.ServiceException;
import models.pojo.Lesson;
import models.pojo.mini.MiniPojo;

import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Сервис работы с уроками
 */
@SuppressWarnings("unused")
public interface LessonService {
    List<Lesson> getAllLessons() throws ServiceException;
    List<Lesson> getCourseLessons(Integer course_id) throws ServiceException;
    List<MiniPojo> getCourseLessonsMini(Integer course_id) throws ServiceException;
    Lesson getLesson(Integer id) throws ServiceException;
    void createLesson(Integer course_id, Integer orderNum, String theme, Double duration, String content) throws ServiceException;
    void updateLesson(Lesson lesson) throws ServiceException;
    void deleteLesson(Integer id) throws ServiceException;
}
