package models.springdata;

import models.entity.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Mordr on 21.03.2017.
 */
public interface LessonRepository extends CrudRepository<Lesson, Integer> {

    List<Lesson> findAllByCourseId(Integer course_id);
}
