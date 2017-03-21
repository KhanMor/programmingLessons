package controllers.mvc;

import common.exceptions.ServiceException;
import models.pojo.CoursePOJO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import services.CourseService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для курсов
 */
@Controller
public class CoursesController {
    private static final Logger logger = Logger.getLogger(CoursesController.class);
    private static final String PRE_POST_AUTHOR_ROLE = "hasRole('ROLE_author')";
    private static final String PRE_POST_ADMIN_ROLE = "hasRole('ROLE_admin')";
    private CourseService courseService;

    @Autowired
    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/courses")
    public String listCourses(Model model) throws ServiceException {
        List<CoursePOJO> courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping(value = "/courses/add")
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public String getAddCoursePage() {
        return "course.add";
    }

    @PostMapping(value = "/courses/add")
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public String addCourse(@Valid CoursePOJO course, BindingResult bindingResult) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "course.add";
        }
        logger.trace("new course was added by user ");
        courseService.createCourse(course);
        return "redirect:/courses";
    }
    @PostMapping(value = "/courses/update")
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public @ResponseBody Integer updateCourse(@Valid CoursePOJO course, Principal principal) throws ServiceException {
        Integer id = course.getId();
        logger.trace("course with id = " + id + " was updated by user " + principal.getName());
        courseService.updateCourse(course);
        return id;
    }
    @DeleteMapping(value = "/courses/delete/{id}")
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public @ResponseBody void deleteCourse(@PathVariable Integer id, Principal principal) throws ServiceException {
        logger.trace("course with id = " + id + " was deleted by user " + principal.getName());
        courseService.deleteCourse(id);
    }
}
