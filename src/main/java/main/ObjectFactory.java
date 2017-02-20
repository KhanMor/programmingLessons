package main;

import models.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
public class ObjectFactory {

    public static User createUser(String firstName, String surName, String patronymic,
                                  Date birthday, String sex, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setSurname(surName);
        user.setPatronymic(patronymic);
        user.setBirthday(birthday);
        user.setSex(sex);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static Role createRole(String roleName, String description) {
        Role role = new Role();
        role.setRole(roleName);
        role.setDescription(description);
        return role;
    }

    public static List<UserRole> createUserRole(User user, Role role) {
        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoles.add(userRole);
        user.setUserRoles(userRoles);
        return userRoles;
    }

    public static Course createCourse(Double duration, User author, String name, Double price, boolean certified) {
        Course course = new Course();
        course.setDuration(duration);
        course.setAuthor(author);
        course.setName(name);
        course.setPrice(price);
        course.setCertified(certified);
        return course;
    }

    public static Lesson createLesson(Double duration, String content, Course course, Integer orderNum, Integer scoreToPass, String theme) {
        Lesson lesson = new Lesson();
        lesson.setDuration(duration);
        lesson.setContent(content);
        lesson.setCourse(course);
        lesson.setOrderNum(orderNum);
        lesson.setScoreToPass(scoreToPass);
        lesson.setTheme(theme);
        return lesson;
    }

    public static LessonTest createLessonTest(Lesson lesson, Integer orderNum, String question, String answer, Integer points) {
        LessonTest lessonTest = new LessonTest();
        lessonTest.setLesson(lesson);
        lessonTest.setOrderNum(orderNum);
        lessonTest.setQuestion(question);
        lessonTest.setAnswer(answer);
        lessonTest.setPoints(points);
        return lessonTest;
    }

    public static LessonTestResult createLessonTestResult(Timestamp testdatetime, LessonTest lessonTest,
                                                          User user, Integer score) {
        LessonTestResult lessonTestResult = new LessonTestResult();
        lessonTestResult.setTestDateTime(testdatetime);
        lessonTestResult.setLessonTest(lessonTest);
        lessonTestResult.setUser(user);
        lessonTestResult.setScore(score);
        return lessonTestResult;
    }
}
