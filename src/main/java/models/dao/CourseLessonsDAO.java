package models.dao;

import common.exceptions.DAOException;
import models.pojo.Lesson;
import models.pojo.mini.MiniPojo;

import java.util.List;

/**
 * Created by Mordr on 10.03.2017.
 */
public interface CourseLessonsDAO {
    List<Lesson> courseLessonsList(Integer course_id) throws DAOException;
    List<MiniPojo> courseLessonsListMini(Integer course_id) throws DAOException;
}
