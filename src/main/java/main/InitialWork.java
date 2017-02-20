package main;

import dao.*;
import daoimpl.*;
import models.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 */
public class InitialWork {
    public static void doInserts(Connection conn) {
        User student1 = ObjectFactory.createUser(
                "Andrey","Kharkhanov", "Sergeevich", Date.valueOf(LocalDate.of(1989,11,30)), "m",
                "kharkhanov@mail.ru", "123"
        );
        User student2 = ObjectFactory.createUser(
                "Dmitiy","Naraikin", "Mikhailovich", Date.valueOf(LocalDate.of(1990,1,1)), "m",
                "naraikin@mail.ru", "123"
        );
        User student3 = ObjectFactory.createUser(
                "Sergey","Lobanov", "Andreevich", Date.valueOf(LocalDate.of(1866,1,1)), "m",
                "lobanov@mail.ru", "123"
        );
        User student4 = ObjectFactory.createUser(
                "Yuriy","Penykov", "Yurievich", Date.valueOf(LocalDate.of(1991,1,1)), "m",
                "penykov@mail.ru", "123"
        );
        User student5 = ObjectFactory.createUser(
                "Denis","Kochanovskiy", "Vladimirovich", Date.valueOf(LocalDate.of(1766,1,1)), "m",
                "kochanovskiy@mail.ru", "123"
        );


        Role student_role = ObjectFactory.createRole("student", "Студент");
        student1.setUserRoles(ObjectFactory.createUserRole(student1, student_role));
        student2.setUserRoles(ObjectFactory.createUserRole(student2, student_role));
        student3.setUserRoles(ObjectFactory.createUserRole(student3, student_role));
        student4.setUserRoles(ObjectFactory.createUserRole(student4, student_role));
        student5.setUserRoles(ObjectFactory.createUserRole(student5, student_role));

        User author = ObjectFactory.createUser(
                "Galileo","Galiley", "", Date.valueOf(LocalDate.of(1564,2,15)), "m",
                "galileo@mail.ru", "123"
        );
        Role author_role = ObjectFactory.createRole("author", "Автор");
        author.setUserRoles(ObjectFactory.createUserRole(author, author_role));

        SuperDAO roleDAO = new RoleDAOImpl(conn);
        roleDAO.insert(student_role);
        roleDAO.insert(author_role);

        SuperDAO userDAO = new UserDAOImpl(conn);
        userDAO.insert(student1);
        userDAO.insert(student2);
        userDAO.insert(student3);
        userDAO.insert(student4);
        userDAO.insert(student5);
        userDAO.insert(author);

        Course course = ObjectFactory.createCourse(300.00, author, "Java developer", 100000.00, true);

        Lesson lesson1 = ObjectFactory.createLesson(4.0, "adxzczsessdgsdgsdgsdgsdg",
                course, 0, 65, "Java basics 2");

        Lesson lesson2 = ObjectFactory.createLesson(3.0, "aaaaaaaaaaaaaaaaaaaaaa",
                course, 1, 75, "Java basics 3");

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson1);
        lessons.add(lesson2);
        course.setLessons(lessons);
        SuperDAO courseDAO = new CourseDAOImpl(conn);
        courseDAO.insert(course);

        LessonTest lessonTest1 = ObjectFactory.createLessonTest(lesson1, 0, "Ыть?", "Ыть", 100);
        LessonTest lessonTest2 = ObjectFactory.createLessonTest(lesson2, 0, "Ыть ыть?", "Ыть ыть", 100);
        List<LessonTest> lessonTests1 = new ArrayList<>();
        lessonTests1.add(lessonTest1);
        lesson1.setLessonTests(lessonTests1);
        List<LessonTest> lessonTests2 = new ArrayList<>();
        lessonTests2.add(lessonTest2);
        lesson2.setLessonTests(lessonTests2);

        SuperDAO lessonTestDAO = new LessonTestDAOImpl(conn);
        lessonTestDAO.insert(lessonTest1);
        lessonTestDAO.insert(lessonTest2);

        SuperDAO lessonTestResultDAO = new LessonTestResultDAOImpl(conn);
        LessonTestResult lessonTestResult1 = ObjectFactory.createLessonTestResult(
                Timestamp.valueOf(LocalDateTime.now()), lessonTest1, student1, 100
        );
        lessonTestResultDAO.insert(lessonTestResult1);
        LessonTestResult lessonTestResult2 = ObjectFactory.createLessonTestResult(
                Timestamp.valueOf(LocalDateTime.now()), lessonTest2, student2, 100
        );
        lessonTestResultDAO.insert(lessonTestResult2);
        LessonTestResult lessonTestResult3 = ObjectFactory.createLessonTestResult(
                Timestamp.valueOf(LocalDateTime.now()), lessonTest1, student3, 100
        );
        lessonTestResultDAO.insert(lessonTestResult3);
    }
}
