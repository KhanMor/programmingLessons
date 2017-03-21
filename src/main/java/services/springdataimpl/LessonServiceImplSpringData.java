package services.springdataimpl;

import common.exceptions.ServiceException;
import models.entity.Course;
import models.entity.Lesson;
import models.pojo.LessonPOJO;
import models.pojo.mini.MiniPOJO;
import models.springdata.CourseRepository;
import models.springdata.LessonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.LessonService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 21.03.2017.
 * Сервис для работы с уроками, использующий репозиторий Spring Data
 */
@Service
public class LessonServiceImplSpringData implements LessonService {
    private static final Logger logger = Logger.getLogger(LessonServiceImplSpringData.class);
    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;

    @Autowired
    public LessonServiceImplSpringData(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<LessonPOJO> getAllLessons() throws ServiceException {
        List<Lesson> lessons = (List<Lesson>) lessonRepository.findAll();
        List<LessonPOJO> lessonPOJOS = new ArrayList<>(lessons.size());
        for (Lesson lesson:lessons) {
            LessonPOJO lessonPOJO = new LessonPOJO(lesson);
            lessonPOJOS.add(lessonPOJO);
        }
        return lessonPOJOS;
    }

    @Override
    public List<LessonPOJO> getCourseLessons(Integer course_id) throws ServiceException {
        List<Lesson> lessons = lessonRepository.findAllByCourseId(course_id);
        List<LessonPOJO> lessonPOJOS = new ArrayList<>(lessons.size());
        for (Lesson lesson:lessons) {
            LessonPOJO lessonPOJO = new LessonPOJO(lesson);
            lessonPOJOS.add(lessonPOJO);
        }
        return lessonPOJOS;
    }

    private String formatLesson(Lesson lesson) {
        return lesson.getOrderNum() + ". " + lesson.getTheme() + " - " + lesson.getDuration();
    }
    @Override
    public List<MiniPOJO> getCourseLessonsMini(Integer course_id) throws ServiceException {
        List<Lesson> lessons = lessonRepository.findAllByCourseId(course_id);
        List<MiniPOJO> miniPOJOS = new ArrayList<>(lessons.size());
        for (Lesson lesson:lessons) {
            MiniPOJO miniPOJO = new MiniPOJO();
            miniPOJO.setId(lesson.getId());
            miniPOJO.setName(formatLesson(lesson));
            miniPOJOS.add(miniPOJO);
        }
        return miniPOJOS;
    }

    @Override
    public LessonPOJO getLesson(Integer id) throws ServiceException {
        Lesson lesson = lessonRepository.findOne(id);
        return new LessonPOJO(lesson);
    }

    @Override
    public void createLesson(Integer course_id, Integer orderNum, String theme, Double duration, String content) throws ServiceException {
        Course course = courseRepository.findOne(course_id);
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setOrderNum(orderNum);
        lesson.setTheme(theme);
        lesson.setDuration(duration);
        lesson.setContent(content);
        lessonRepository.save(lesson);
    }

    @Override
    public void updateLesson(LessonPOJO lessonPOJO) throws ServiceException {
        Integer id = lessonPOJO.getId();
        Lesson lesson = lessonRepository.findOne(id);
        lesson.setOrderNum(lessonPOJO.getOrderNum());
        lesson.setTheme(lessonPOJO.getTheme());
        lesson.setDuration(lessonPOJO.getDuration());
        lesson.setContent(lessonPOJO.getContent());
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Integer id) throws ServiceException {
        lessonRepository.delete(id);
    }
}
