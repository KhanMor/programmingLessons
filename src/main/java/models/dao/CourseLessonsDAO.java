package models.dao;

import common.exceptions.DAOException;
import models.entity.Lesson;
import models.pojo.mini.MiniPOJO;

import java.util.List;

/**
 * Created by Mordr on 10.03.2017.
 */
public interface CourseLessonsDAO {
    List<Lesson> courseLessonsList(Integer course_id) throws DAOException;
    //List<MiniPOJO> courseLessonsListMini(Integer course_id) throws DAOException;
}
