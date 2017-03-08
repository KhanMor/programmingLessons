package controllers.mvc;

import common.exceptions.ServiceException;
import models.pojo.Course;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.CourseService;
import services.UserService;

import java.util.List;

/**
 * Created by Mordr on 07.03.2017.
 * Контроллер для курсов
 */
@Controller
public class CoursesController {
    private static final Logger logger = Logger.getLogger(CoursesController.class);
    private static final String AUTH_ATTRIBUTE_NAME = "user";
    private CourseService courseService;
    private UserService userService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String listCourses(Model model) throws ServiceException {
        List<Course> courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @RequestMapping(value = "/courses/add", method = RequestMethod.GET)
    public String getAddCoursePage() {
        return "course.add";
    }
    @RequestMapping(value = "/courses/add", method = RequestMethod.POST)
    public String addCourse(@SessionAttribute(AUTH_ATTRIBUTE_NAME) String email,
                            @RequestParam String name, @RequestParam Double duration) throws ServiceException {
        User author = userService.getUserByEmail(email);
        Course course = new Course(author, name, duration);
        courseService.createCourse(course);
        return "redirect:/courses";
    }
    @RequestMapping(value = "/courses/update", method = RequestMethod.POST)
    public @ResponseBody void updateCourse(@ModelAttribute Course course) throws ServiceException {
        courseService.updateCourse(course);
    }
    @RequestMapping(value = "/courses/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteCourse(@PathVariable Integer id) throws ServiceException {
        courseService.deleteCourse(id);
    }
}
