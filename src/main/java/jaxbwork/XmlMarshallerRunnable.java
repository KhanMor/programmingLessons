package jaxbwork;

import dao.*;
import jaxbwork.jaxbwrappers.*;
import models.*;

/**
 * Created by Mordr on 19.02.2017.
 */
public class XmlMarshallerRunnable<T> implements Runnable {
    private final SuperDAO superDAO;
    private final String filename;
    private final T t;

    public XmlMarshallerRunnable(SuperDAO superDAO, String filename, T t) {
        this.superDAO = superDAO;
        this.filename = filename;
        this.t = t;
    }

    @Override
    public void run() {
        if(t instanceof User) {
            UsersWrapper usersWrapper = new UsersWrapper(superDAO.list());
            usersWrapper.xmlMarshall(filename);
        } else
        if(t instanceof Role) {
            RolesWrapper rolesWrapper = new RolesWrapper(superDAO.list());
            rolesWrapper.xmlMarshall(filename);
        } else
        if(t instanceof Course) {
            CoursesWrapper coursesWrapper = new CoursesWrapper(superDAO.list());
            coursesWrapper.xmlMarshall(filename);
        } else
        if(t instanceof Lesson) {
            LessonsWrapper lessonsWrapper = new LessonsWrapper(superDAO.list());
            lessonsWrapper.xmlMarshall(filename);
        } else
        if(t instanceof LessonTest) {
            LessonTestsWrapper lessonTestsWrapper = new LessonTestsWrapper(superDAO.list());
            lessonTestsWrapper.xmlMarshall(filename);
        } else
        if(t instanceof LessonTestResult) {
            LessonTestResultsWrapper lessonTestResultsWrapper = new LessonTestResultsWrapper(superDAO.list());
            lessonTestResultsWrapper.xmlMarshall(filename);
        }
    }
}
