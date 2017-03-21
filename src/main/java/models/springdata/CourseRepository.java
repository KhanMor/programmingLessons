package models.springdata;

import models.entity.Course;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mordr on 21.03.2017.
 */
public interface CourseRepository extends CrudRepository<Course, Integer> {
}
