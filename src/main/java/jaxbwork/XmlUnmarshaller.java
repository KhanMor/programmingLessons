package jaxbwork;

import dao.SuperDAO;
import jaxbwork.jaxbwrappers.*;
import models.*;

import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 */
public class XmlUnmarshaller<T> {
    private final SuperDAO superDAO;
    private final String filename;
    private final T t;

    public XmlUnmarshaller(SuperDAO superDAO, String filename, T t) {
        this.superDAO = superDAO;
        this.filename = filename;
        this.t = t;
    }

    public void doUnmarshall() {
        if(t instanceof User) {
            UsersWrapper usersWrapper = new UsersWrapper();
            usersWrapper = (UsersWrapper)usersWrapper.xmlUnmarshall(filename);
            List<User> users = usersWrapper.getObjects();
            for(User user:users) {
                superDAO.insert(user);
            }
        } else
        if(t instanceof Role) {
            RolesWrapper rolesWrapper = new RolesWrapper(superDAO.list());
            rolesWrapper = (RolesWrapper)rolesWrapper.xmlUnmarshall(filename);
            List<Role> roles = rolesWrapper.getObjects();
            for(Role role:roles) {
                superDAO.insert(role);
            }
        } else
        if(t instanceof UserRole) {
            UserRolesWrapper userRolesWrapper = new UserRolesWrapper(superDAO.list());
            userRolesWrapper = (UserRolesWrapper)userRolesWrapper.xmlUnmarshall(filename);
            List<UserRole> userRoles = userRolesWrapper.getObjects();
            for(UserRole userRole:userRoles) {
                superDAO.insert(userRole);
            }
        } else
        if(t instanceof Course) {
            CoursesWrapper coursesWrapper = new CoursesWrapper(superDAO.list());
            coursesWrapper = (CoursesWrapper)coursesWrapper.xmlUnmarshall(filename);
            List<Course> courses = coursesWrapper.getObjects();
            for(Course course:courses) {
                superDAO.insert(course);
            }
        } else
        if(t instanceof Lesson) {
            LessonsWrapper lessonsWrapper = new LessonsWrapper(superDAO.list());
            lessonsWrapper = (LessonsWrapper)lessonsWrapper.xmlUnmarshall(filename);
            List<Lesson> lessons = lessonsWrapper.getObjects();
            for(Lesson lesson:lessons) {
                superDAO.insert(lesson);
            }
        } else
        if(t instanceof LessonTest) {
            LessonTestsWrapper lessonTestsWrapper = new LessonTestsWrapper(superDAO.list());
            lessonTestsWrapper = (LessonTestsWrapper)lessonTestsWrapper.xmlUnmarshall(filename);
            List<LessonTest> lessonTests = lessonTestsWrapper.getObjects();
            for(LessonTest lessonTest:lessonTests) {
                superDAO.insert(lessonTest);
            }
        } else
        if(t instanceof LessonTestResult) {
            LessonTestResultsWrapper lessonTestResultsWrapper = new LessonTestResultsWrapper(superDAO.list());
            lessonTestResultsWrapper = (LessonTestResultsWrapper)lessonTestResultsWrapper.xmlUnmarshall(filename);
            List<LessonTestResult> lessonTestResults = lessonTestResultsWrapper.getObjects();
            for(LessonTestResult lessonTestResult:lessonTestResults) {
                superDAO.insert(lessonTestResult);
            }
        }
    }
}
