package jaxbwork;

import common.exceptions.DAOException;
import jaxbwork.jaxbwrappers.*;
import models.dao.SuperDAO;
import models.pojo.*;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 * Поток для десериализации xml в БД
 */
public class XmlUnmarshallerRunnable<T> implements Runnable {
    private final SuperDAO superDAO;
    private final String filename;
    private final T t;
    private final InsertedWrapper insertedWrapper;
    private static final Logger logger = Logger.getLogger(XmlUnmarshallerRunnable.class);

    public XmlUnmarshallerRunnable(SuperDAO superDAO, String filename, T t, InsertedWrapper insertedWrapper) {
        this.superDAO = superDAO;
        this.filename = filename;
        this.t = t;
        this.insertedWrapper = insertedWrapper;
    }

    @Override
    public void run() {
        try {
            logger.trace("thread started for " + filename);
            if (t instanceof User) {
                UsersWrapper usersWrapper = new UsersWrapper();
                usersWrapper = (UsersWrapper) usersWrapper.xmlUnmarshall(filename);
                List<User> users = usersWrapper.getObjects();
                for (User user : users) {
                    synchronized (insertedWrapper) {
                        superDAO.insert(user);
                        insertedWrapper.add(user);
                        insertedWrapper.notifyAll();
                        logger.trace(user + " inserted");
                    }
                }
            } else if (t instanceof Role) {
                RolesWrapper rolesWrapper = new RolesWrapper(superDAO.list());
                rolesWrapper = (RolesWrapper) rolesWrapper.xmlUnmarshall(filename);
                List<Role> roles = rolesWrapper.getObjects();
                for (Role role : roles) {
                    synchronized (insertedWrapper) {
                        superDAO.insert(role);
                        insertedWrapper.add(role);
                        insertedWrapper.notifyAll();
                        logger.trace(role + " inserted");
                    }
                }
            } else if (t instanceof UserRole) {
                UserRolesWrapper userRolesWrapper = new UserRolesWrapper(superDAO.list());
                userRolesWrapper = (UserRolesWrapper) userRolesWrapper.xmlUnmarshall(filename);
                List<UserRole> userRoles = userRolesWrapper.getObjects();
                for (UserRole userRole : userRoles) {
                    synchronized (insertedWrapper) {
                        User user = userRole.getUser();
                        Role role = userRole.getRole();
                        try {
                            while (!insertedWrapper.contains(user) || !insertedWrapper.contains(role)) {
                                logger.trace("User " + user + " or role " + role + " not founded, waiting inserts...");
                                insertedWrapper.wait();
                            }
                            logger.trace("wake up!");
                        } catch (InterruptedException e) {
                            logger.error(e);
                        }

                        superDAO.insert(userRole);
                        insertedWrapper.add(userRole);
                        insertedWrapper.notifyAll();
                        logger.trace(userRole + " inserted");
                    }
                }
            } else if (t instanceof Course) {
                CoursesWrapper coursesWrapper = new CoursesWrapper(superDAO.list());
                coursesWrapper = (CoursesWrapper) coursesWrapper.xmlUnmarshall(filename);
                List<Course> courses = coursesWrapper.getObjects();
                for (Course course : courses) {
                    synchronized (insertedWrapper) {
                        User author = course.getAuthor();
                        while (!insertedWrapper.contains(author)) {
                            try {
                                insertedWrapper.wait();
                            } catch (InterruptedException e) {
                                logger.error(e);
                            }
                        }
                        superDAO.insert(course);
                        insertedWrapper.add(course);
                        insertedWrapper.notifyAll();
                        logger.trace(course + " inserted");
                    }
                }
            } else if (t instanceof Lesson) {
                LessonsWrapper lessonsWrapper = new LessonsWrapper(superDAO.list());
                lessonsWrapper = (LessonsWrapper) lessonsWrapper.xmlUnmarshall(filename);
                List<Lesson> lessons = lessonsWrapper.getObjects();
                for (Lesson lesson : lessons) {
                    synchronized (insertedWrapper) {
                        Course course = lesson.getCourse();
                        while (!insertedWrapper.contains(course)) {
                            try {
                                insertedWrapper.wait();
                            } catch (InterruptedException e) {
                                logger.error(e);
                            }
                        }
                        superDAO.insert(lesson);
                        insertedWrapper.add(lesson);
                        insertedWrapper.notifyAll();
                        logger.trace(lesson + " inserted");
                    }
                }
            }
        } catch (
                DAOException e) {
            e.printStackTrace();
        }
    }
}
