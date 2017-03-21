package controllers.mvc;

import models.pojo.CoursePOJO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import services.CourseService;

/**
 * Created by Mordr on 17.03.2017.
 */

class CoursesControllerTest {
    private CourseService courseService;

    @Autowired
    public CoursesControllerTest(CourseService courseService) {
        this.courseService = courseService;
    }

    @Test
    void addCourse() {
        CoursePOJO coursePOJO = new CoursePOJO();
        coursePOJO.setName("oversizetesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
        coursePOJO.setDuration(-1.0);
        CoursesController coursesController = new CoursesController(courseService);
    }

    @Test
    void updateCourse() {

    }

    @Test
    void deleteCourse() {

    }

}