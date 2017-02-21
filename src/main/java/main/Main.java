package main;

import config.DatabaseConnector;
import dao.*;
import daoimpl.*;
import jaxbwork.InsertedWrapper;
import jaxbwork.XmlMarshallerRunnable;
import jaxbwork.XmlUnmarshaller;
import jaxbwork.XmlUnmarshallerRunnable;
import models.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;
import java.sql.*;
import java.util.HashSet;

/**
 * Created by Mordr on 16.02.2017.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    static  {
        DOMConfigurator.configure("src/main/resources/log4j.xml");
    }

    private static final DatabaseConnector databaseConnector=DatabaseConnector.getInstance();
    private static final Connection CONN = databaseConnector.getConnection();
    private static final SuperDAO USER_DAO = new UserDAOImpl(CONN);
    private static final SuperDAO ROLE_DAO = new RoleDAOImpl(CONN);
    private static final SuperDAO USER_ROLE_DAO = new UserRoleDAOImpl(CONN);
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
        logger.trace("database export to xml files");
        File xmlout = new File(XML_OUTPUT_FOLDER);
        if (!xmlout.exists()) {
            xmlout.mkdirs();
        }
        startMarshallerThread(USER_DAO,XML_OUTPUT_FOLDER + "/users.xml", new User());
        startMarshallerThread(ROLE_DAO,XML_OUTPUT_FOLDER + "/roles.xml", new Role());
        startMarshallerThread(USER_ROLE_DAO,XML_OUTPUT_FOLDER + "/userRoles.xml", new UserRole());
        startMarshallerThread(COURSE_DAO,XML_OUTPUT_FOLDER + "/courses.xml", new Course());
        startMarshallerThread(LESSON_DAO,XML_OUTPUT_FOLDER + "/lessons.xml", new Lesson());
        startMarshallerThread(LESSON_TEST_DAO,XML_OUTPUT_FOLDER + "/lessontests.xml", new LessonTest());
        startMarshallerThread(LESSON_TEST_RESULT_DAO,XML_OUTPUT_FOLDER + "/lessontestsresult.xml", new LessonTestResult());
    }

    private static void doDelete() {
        logger.trace("delete all from database...");
        LESSON_TEST_RESULT_DAO.deleteAll();
        LESSON_TEST_DAO.deleteAll();
        LESSON_DAO.deleteAll();
        COURSE_DAO.deleteAll();
        USER_ROLE_DAO.deleteAll();
        ROLE_DAO.deleteAll();
        USER_DAO.deleteAll();
    }

    private static  void startUnmarshallerThread(SuperDAO dao, String filename, Object object, InsertedWrapper insertedWrapper) {
        XmlUnmarshallerRunnable xur = new XmlUnmarshallerRunnable(dao, filename, object, insertedWrapper);
        Thread thread = new Thread(xur);
        thread.start();
    }

    private static void doUnmarshaller(SuperDAO dao, String filename, Object object) {
        XmlUnmarshaller xmlUnmarshaller = new XmlUnmarshaller(dao, filename, object);
        xmlUnmarshaller.doUnmarshall();
    }

    private static void xmlIn() {
        logger.trace("insert to database from xml files");
        HashSet<Object> insertedObjects = new HashSet<>();
        InsertedWrapper insertedWrapper = new InsertedWrapper(insertedObjects);
        startUnmarshallerThread(ROLE_DAO,XML_OUTPUT_FOLDER + "/roles.xml", new Role(), insertedWrapper);
        startUnmarshallerThread(USER_DAO,XML_OUTPUT_FOLDER + "/users.xml", new User(), insertedWrapper);
        startUnmarshallerThread(USER_ROLE_DAO,XML_OUTPUT_FOLDER + "/userRoles.xml", new UserRole(), insertedWrapper);
        startUnmarshallerThread(COURSE_DAO,XML_OUTPUT_FOLDER + "/courses.xml", new Course(), insertedWrapper);
        startUnmarshallerThread(LESSON_DAO,XML_OUTPUT_FOLDER + "/lessons.xml", new Lesson(), insertedWrapper);
        startUnmarshallerThread(LESSON_TEST_DAO,XML_OUTPUT_FOLDER + "/lessontests.xml", new LessonTest(), insertedWrapper);
        startUnmarshallerThread(LESSON_TEST_RESULT_DAO,XML_OUTPUT_FOLDER + "/lessontestsresult.xml", new LessonTestResult(), insertedWrapper);
    }

    public static void main(String[] args) {
        //InitialWork.doInserts(CONN);
        //xmlOut();
        doDelete();
        xmlIn();
    }
}
