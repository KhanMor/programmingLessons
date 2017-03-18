package services;

import common.exceptions.ServiceException;
import models.pojo.CoursePOJO;

import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Сервис работы с курсами
 */
@SuppressWarnings("unused")
public interface CourseService {
    List<CoursePOJO> getCourses() throws ServiceException;
    CoursePOJO getCourse(Integer id) throws ServiceException;
    void createCourse(CoursePOJO coursePOJO) throws ServiceException;
    void updateCourse(CoursePOJO coursePOJO) throws ServiceException;
    void deleteCourse(Integer id) throws ServiceException;
}
