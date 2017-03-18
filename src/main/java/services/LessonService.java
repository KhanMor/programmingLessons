package services;

import common.exceptions.ServiceException;
import models.pojo.LessonPOJO;
import models.pojo.mini.MiniPOJO;

import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Сервис работы с уроками
 */
@SuppressWarnings("unused")
public interface LessonService {
    List<LessonPOJO> getAllLessons() throws ServiceException;
    List<LessonPOJO> getCourseLessons(Integer course_id) throws ServiceException;
    List<MiniPOJO> getCourseLessonsMini(Integer course_id) throws ServiceException;
    LessonPOJO getLesson(Integer id) throws ServiceException;
    void createLesson(Integer course_id, Integer orderNum, String theme, Double duration, String content) throws ServiceException;
    void updateLesson(LessonPOJO lessonPOJO) throws ServiceException;
    void deleteLesson(Integer id) throws ServiceException;
}
