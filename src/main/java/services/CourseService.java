package services;

import common.exceptions.ServiceException;
import models.pojo.Course;

import java.util.List;

/**
 * Created by Mordr on 03.03.2017.
 * Сервис работы с курсами
 */
@SuppressWarnings("unused")
public interface CourseService {
    List<Course> getCourses() throws ServiceException;
    Course getCourse(Integer id) throws ServiceException;
    void createCourse(Course course) throws ServiceException;
    void updateCourse(Course course) throws ServiceException;
    void deleteCourse(Integer id) throws ServiceException;
}
