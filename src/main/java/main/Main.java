package main;

import config.DatabaseConnector;
import dao.*;
import daoimpl.*;
import jaxbwork.XmlMarshallerRunnable;
import jaxbwork.XmlUnmarshaller;
import jaxbwork.XmlUnmarshallerRunnable;
import models.*;

import java.io.File;
import java.sql.*;

/**
 * Created by Mordr on 16.02.2017.
 */
public class Main {
    private static final DatabaseConnector databaseConnector=DatabaseConnector.getInstance();
    private static final Connection CONN = databaseConnector.getConnection();
    private static final SuperDAO USER_DAO = new UserDAOImpl(CONN);
    private static final SuperDAO ROLE_DAO = new RoleDAOImpl(CONN);
    private static final SuperDAO COURSE_DAO = new CourseDAOImpl(CONN);
    private static final SuperDAO LESSON_DAO = new LessonDAOImpl(CONN);
    private static final SuperDAO LESSON_TEST_DAO = new LessonTestDAOImpl(CONN);
    private static final SuperDAO LESSON_TEST_RESULT_DAO = new LessonTestResultDAOImpl(CONN);
    private static final String XML_OUTPUT_FOLDER = "xmlout";

    private static  void startMarshallerThread(SuperDAO dao, String filename, Object object) {
        XmlMarshallerRunnable xmr = new XmlMarshallerRunnable(dao, filename, object);
        Thread thread = new Thread(xmr);
        thread.start();
    }

    private static void xmlOut() {
        File xmlout = new File(XML_OUTPUT_FOLDER);
        if (!xmlout.exists()) {
            xmlout.mkdirs();
        }
        startMarshallerThread(USER_DAO,XML_OUTPUT_FOLDER + "/users.xml", new User());
        startMarshallerThread(ROLE_DAO,XML_OUTPUT_FOLDER + "/roles.xml", new Role());
        startMarshallerThread(COURSE_DAO,XML_OUTPUT_FOLDER + "/courses.xml", new Course());
        startMarshallerThread(LESSON_DAO,XML_OUTPUT_FOLDER + "/lessons.xml", new Lesson());
        startMarshallerThread(LESSON_TEST_DAO,XML_OUTPUT_FOLDER + "/lessontests.xml", new LessonTest());
        startMarshallerThread(LESSON_TEST_RESULT_DAO,XML_OUTPUT_FOLDER + "/lessontestsresult.xml", new LessonTestResult());
    }

    private static void doDelete() {
        LESSON_TEST_RESULT_DAO.deleteAll();
        LESSON_TEST_DAO.deleteAll();
        LESSON_DAO.deleteAll();
        COURSE_DAO.deleteAll();
        UserRoleDAO userRoleDAO = new UserRoleDAOImpl(CONN);
        userRoleDAO.deleteAll();
        ROLE_DAO.deleteAll();
        USER_DAO.deleteAll();
    }

    private static  void startUnmarshallerThread(SuperDAO dao, String filename, Object object) {
        XmlUnmarshallerRunnable xur = new XmlUnmarshallerRunnable(dao, filename, object);
        Thread thread = new Thread(xur);
        thread.start();
    }

    private static void doUnmarshaller(SuperDAO dao, String filename, Object object) {
        XmlUnmarshaller xmlUnmarshaller = new XmlUnmarshaller(dao, filename, object);
        xmlUnmarshaller.doUnmarshall();
    }

    private static void xmlIn() {
        //startUnmarshallerThread(ROLE_DAO,XML_OUTPUT_FOLDER + "/roles.xml", new Role());
        //startUnmarshallerThread(USER_DAO,XML_OUTPUT_FOLDER + "/users.xml", new User());
        //startUnmarshallerThread(COURSE_DAO,XML_OUTPUT_FOLDER + "/courses.xml", new Course());
        //startUnmarshallerThread(LESSON_DAO,XML_OUTPUT_FOLDER + "/lessons.xml", new Lesson());
        //startUnmarshallerThread(LESSON_TEST_DAO,XML_OUTPUT_FOLDER + "/lessontests.xml", new LessonTest());
        //startUnmarshallerThread(LESSON_TEST_RESULT_DAO,XML_OUTPUT_FOLDER + "/lessontestsresult.xml", new LessonTestResult());
        doUnmarshaller(ROLE_DAO,XML_OUTPUT_FOLDER + "/roles.xml", new Role());
        doUnmarshaller(USER_DAO,XML_OUTPUT_FOLDER + "/users.xml", new User());
        doUnmarshaller(COURSE_DAO,XML_OUTPUT_FOLDER + "/courses.xml", new Course());
        doUnmarshaller(LESSON_TEST_RESULT_DAO,XML_OUTPUT_FOLDER + "/lessontestsresult.xml", new LessonTestResult());
    }

    public static void main(String[] args) {
        //InitialWork.doInserts(CONN);
        //xmlOut();
        doDelete();
        //xmlIn();
    }
}
