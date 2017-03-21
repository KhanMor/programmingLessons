package services.springdataimpl;

import common.exceptions.ServiceException;
import models.entity.Course;
import models.entity.User;
import models.pojo.CoursePOJO;
import models.springdata.CourseRepository;
import models.springdata.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import services.CourseService;
import springconfig.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 21.03.2017.
 * Сервис для работы с курсами, использующий репозиторий Spring Data
 */
@Service
public class CourseServiceImplSpringData implements CourseService {
    private static final Logger logger = Logger.getLogger(CourseServiceImplSpringData.class);
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Autowired
    public CourseServiceImplSpringData(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CoursePOJO> getCourses() throws ServiceException {
        List<Course> courses = (List<Course>) courseRepository.findAll();
        List<CoursePOJO> coursePOJOS = new ArrayList<>(courses.size());
        for (Course course:courses) {
            User author = userRepository.findOne(course.getAuthor().getId());
            course.setAuthor(author);
            CoursePOJO coursePOJO = new CoursePOJO(course);
            coursePOJOS.add(coursePOJO);
        }
        return coursePOJOS;
    }

    @Override
    public CoursePOJO getCourse(Integer id) throws ServiceException {
        Course course = courseRepository.findOne(id);
        User author = userRepository.findOne(course.getAuthor().getId());
        course.setAuthor(author);
        return new CoursePOJO(course);
    }

    @Override
    public void createCourse(CoursePOJO coursePOJO) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.trace("new course was added by user ");
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            User user = securityUser.getUser();
            Course course = new Course();
            course.setName(coursePOJO.getName());
            course.setDuration(coursePOJO.getDuration());
            course.setAuthor(user);
            courseRepository.save(course);
        }
    }

    @Override
    public void updateCourse(CoursePOJO coursePOJO) throws ServiceException {
        Integer id = coursePOJO.getId();
        Course course = courseRepository.findOne(id);
        course.setName(coursePOJO.getName());
        course.setDuration(coursePOJO.getDuration());
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Integer id) throws ServiceException {
        courseRepository.delete(id);
    }
}
