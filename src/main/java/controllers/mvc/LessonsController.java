package controllers.mvc;

import common.exceptions.ServiceException;
import models.pojo.Lesson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.LessonService;

import java.util.List;

/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для уроков
 */
@Controller
public class LessonsController {
    private static final Logger logger = Logger.getLogger(LessonsController.class);
    private LessonService lessonService;

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    public String listCourses(Model model) throws ServiceException {
        List<Lesson> lessons = lessonService.getLessons();
        model.addAttribute("lessons", lessons);
        return "lessons";
    }
    @RequestMapping(value = "/lessons/add", method = RequestMethod.PUT)
    public @ResponseBody Integer addCourse(@ModelAttribute Lesson lesson) throws ServiceException {
        lessonService.createLesson(lesson);
        return lesson.getId();
    }
    @RequestMapping(value = "/lessons/update", method = RequestMethod.POST)
    public @ResponseBody void updateCourse(@ModelAttribute Lesson lesson) throws ServiceException {
        lessonService.updateLesson(lesson);
    }
    @RequestMapping(value = "/lessons/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteCourse(@PathVariable Integer id) throws ServiceException {
        lessonService.deleteLesson(id);
    }
}
