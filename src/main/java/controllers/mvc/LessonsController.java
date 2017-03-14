package controllers.mvc;

import common.exceptions.ServiceException;
import models.pojo.Course;
import models.pojo.Lesson;
import models.pojo.mini.MiniPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.CourseService;
import services.LessonService;

import java.util.List;

/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для уроков
 */
@Controller
public class LessonsController {
    private static final Logger logger = Logger.getLogger(LessonsController.class);
    private static final String PRE_POST_STUDENT_ROLE = "hasRole('ROLE_student')";
    private static final String PRE_POST_AUTHOR_ROLE = "hasRole('ROLE_author')";
    private static final String PRE_POST_ADMIN_ROLE = "hasRole('ROLE_admin')";
    private LessonService lessonService;
    private CourseService courseService;

    @Autowired
    public LessonsController(LessonService lessonService, CourseService courseService) {
        this.lessonService = lessonService;
        this.courseService = courseService;
    }

    @RequestMapping(value = "/lessons/all", method = RequestMethod.GET)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE + " or " + PRE_POST_STUDENT_ROLE)
    public String listAllLessons(Model model) throws ServiceException {
        List<Lesson> lessons = lessonService.getAllLessons();
        model.addAttribute("lessons", lessons);

        return "lessons";
    }
    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE + " or " + PRE_POST_STUDENT_ROLE)
    public String listCourseLessons(@RequestParam Integer course_id, Model model) throws ServiceException {
        List<MiniPojo> lessons = lessonService.getCourseLessonsMini(course_id);
        Course course = courseService.getCourse(course_id);
        model.addAttribute("course", course);
        model.addAttribute("lessons", lessons);
        return "lessons";
    }
    @RequestMapping(value = "/lessons/get/{id}", method = RequestMethod.GET)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE + " or " + PRE_POST_STUDENT_ROLE)
    public String viewLesson(@PathVariable Integer id, Model model) throws ServiceException {
        Lesson lesson = lessonService.getLesson(id);
        model.addAttribute("lesson", lesson);
        return "lesson";
    }

    @RequestMapping(value = "/lessons/add", method = RequestMethod.GET)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public String getAddLessonPage(@RequestParam Integer course_id, @RequestParam String course_name, Model model) throws ServiceException {
        model.addAttribute("course_id", course_id);
        model.addAttribute("course_name", course_name);
        return "lesson.add";
    }
    @RequestMapping(value = "/lessons/add", method = RequestMethod.POST)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public String addLesson(@RequestParam Integer course_id, @RequestParam Integer orderNum, @RequestParam String theme,
                            @RequestParam Double duration, @RequestParam String content) throws ServiceException {
        lessonService.createLesson(course_id, orderNum, theme, duration, content);
        return "redirect:/lessons?course_id=" + course_id;
    }

    @RequestMapping(value = "/lessons/edit", method = RequestMethod.GET)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public String getEditLessonPage(@RequestParam Integer course_id, @RequestParam String course_name,
                                    @RequestParam Integer id, Model model) throws ServiceException {
        model.addAttribute("course_id", course_id);
        model.addAttribute("course_name", course_name);
        Lesson lesson = lessonService.getLesson(id);
        model.addAttribute("lesson", lesson);
        return "lesson.edit";
    }

    @RequestMapping(value = "/lessons/edit", method = RequestMethod.POST)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public String editLesson(@RequestParam Integer id, @RequestParam Integer course_id, @RequestParam Integer orderNum, @RequestParam String theme,
                            @RequestParam Double duration, @RequestParam String content) throws ServiceException {
        Lesson lesson = lessonService.getLesson(id);
        lesson.setOrderNum(orderNum);
        lesson.setTheme(theme);
        lesson.setDuration(duration);
        lesson.setContent(content);
        lessonService.updateLesson(lesson);
        return "redirect:/lessons?course_id=" + course_id;
    }

    @RequestMapping(value = "/lessons/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public @ResponseBody void deleteLesson(@PathVariable Integer id) throws ServiceException {
        lessonService.deleteLesson(id);
    }
}
