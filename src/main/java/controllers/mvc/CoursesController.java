package controllers.mvc;

import common.exceptions.ServiceException;
import models.pojo.Course;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.CourseService;
import services.UserService;

import java.security.Principal;
import java.util.List;

/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для курсов
 */
@Controller
public class CoursesController {
    private static final Logger logger = Logger.getLogger(CoursesController.class);
    //private static final String AUTH_ATTRIBUTE_NAME = "user";
    private static final String PRE_POST_AUTHOR_ROLE = "hasRole('ROLE_author')";
    private static final String PRE_POST_ADMIN_ROLE = "hasRole('ROLE_admin')";
    private CourseService courseService;
    private UserService userService;

    @Autowired
    public CoursesController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String listCourses(Model model) throws ServiceException {
        List<Course> courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @RequestMapping(value = "/courses/add", method = RequestMethod.GET)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public String getAddCoursePage() {
        return "course.add";
    }

    @RequestMapping(value = "/courses/add", method = RequestMethod.POST)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public String addCourse(//@SessionAttribute(AUTH_ATTRIBUTE_NAME) String email,
                            Principal principal,
                            @RequestParam String name, @RequestParam Double duration) throws ServiceException {
        String email = principal.getName();
        User author = userService.getUserByEmail(email);
        Course course = new Course(author, name, duration);
        logger.trace("new course '" + name + "' was added by user " + principal.getName());
        courseService.createCourse(course);
        return "redirect:/courses";
    }
    @RequestMapping(value = "/courses/update", method = RequestMethod.POST)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public @ResponseBody void updateCourse(@ModelAttribute Course course, Principal principal) throws ServiceException {
        Integer id = course.getId();
        logger.trace("course with id = " + id + " was updated by user " + principal.getName());
        courseService.updateCourse(course);
    }
    @RequestMapping(value = "/courses/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize(PRE_POST_ADMIN_ROLE + " or " + PRE_POST_AUTHOR_ROLE)
    public @ResponseBody void deleteCourse(@PathVariable Integer id, Principal principal) throws ServiceException {
        logger.trace("course with id = " + id + " was deleted by user " + principal.getName());
        courseService.deleteCourse(id);
    }
}
