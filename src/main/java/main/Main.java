package main;

import common.exceptions.DAOException;
import models.connector.DatabaseConnector;
import jaxbwork.InsertedWrapper;
import jaxbwork.XmlMarshallerRunnable;
import jaxbwork.XmlUnmarshallerRunnable;
import models.dao.SuperDAO;
import models.daoimpl.*;
import models.pojo.*;
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
    private static final DatabaseConnector DATABASE_CONNECTOR =DatabaseConnector.getInstance();
    private static final Connection CONN = DATABASE_CONNECTOR.getConnection();
    private static final SuperDAO USER_DAO = new UserDAOImpl();
    private static final SuperDAO ROLE_DAO = new RoleDAOImpl();
    private static final SuperDAO USER_ROLE_DAO = new UserRoleDAOImpl();
    private static final SuperDAO COURSE_DAO = new CourseDAOImpl();
    private static final SuperDAO LESSON_DAO = new LessonDAOImpl();
    private static final SuperDAO LESSON_TEST_DAO = new LessonTestDAOImpl();
    private static final SuperDAO LESSON_TEST_RESULT_DAO = new LessonTestResultDAOImpl();
    private static final String XML_OUTPUT_FOLDER = "xmlout";

    private static  void startMarshallerThread(SuperDAO dao, String filename, Object object) {
        XmlMarshallerRunnable xmr = new XmlMarshallerRunnable(dao, filename, object);
        Thread thread = new Thread(xmr);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    private static void xmlOut() {
        logger.trace("started database export to xml files");
        File xmlout = new File(XML_OUTPUT_FOLDER);
        if (!xmlout.exists()) {
            boolean output_created = xmlout.mkdirs();
            if (output_created) {
                logger.trace(XML_OUTPUT_FOLDER + " directory created");
            }
        }
        startMarshallerThread(USER_DAO,XML_OUTPUT_FOLDER + "/users.xml", new User());
        startMarshallerThread(ROLE_DAO,XML_OUTPUT_FOLDER + "/roles.xml", new Role());
        startMarshallerThread(USER_ROLE_DAO,XML_OUTPUT_FOLDER + "/userRoles.xml", new UserRole());
        startMarshallerThread(COURSE_DAO,XML_OUTPUT_FOLDER + "/courses.xml", new Course());
        startMarshallerThread(LESSON_DAO,XML_OUTPUT_FOLDER + "/lessons.xml", new Lesson());
        startMarshallerThread(LESSON_TEST_DAO,XML_OUTPUT_FOLDER + "/lessontests.xml", new LessonTest());
        startMarshallerThread(LESSON_TEST_RESULT_DAO,XML_OUTPUT_FOLDER + "/lessontestsresult.xml", new LessonTestResult());
        logger.trace("finished database export to xml files");
    }

    private static void doDelete() {
        logger.trace("delete all from database...");
        try {
            LESSON_TEST_RESULT_DAO.deleteAll();
            LESSON_TEST_DAO.deleteAll();
            LESSON_DAO.deleteAll();
            COURSE_DAO.deleteAll();
            USER_ROLE_DAO.deleteAll();
            ROLE_DAO.deleteAll();
            USER_DAO.deleteAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private static  void startUnmarshallerThread(SuperDAO dao, String filename, Object object, InsertedWrapper insertedWrapper) {
        XmlUnmarshallerRunnable xur = new XmlUnmarshallerRunnable(dao, filename, object, insertedWrapper);
        Thread thread = new Thread(xur);
        thread.start();
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
        xmlOut();
        doDelete();
        xmlIn();
    }
}
