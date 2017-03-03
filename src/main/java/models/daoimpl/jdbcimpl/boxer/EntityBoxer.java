package models.daoimpl.jdbcimpl.boxer;

import common.exceptions.DAOException;
import models.dao.SuperDAO;
import models.pojo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 17.02.2017.
 * Велосипед. Пакует resultSet'ы в объекты моделей
 */
@Component
public final class EntityBoxer {
    private static final Logger logger = Logger.getLogger(EntityBoxer.class);
    private static final String GET_COURSE_LESSONS_SQL;
    static {
        GET_COURSE_LESSONS_SQL = "select * from lesson where course_id = ?";
    }

    private static SuperDAO<User> userDAO;
    private static SuperDAO<Role> roleDAO;
    private static SuperDAO<Course> courseDAO;
    private SuperDAO<User> _userDAO;
    private SuperDAO<Role> _roleDAO;
    private SuperDAO<Course> _courseDAO;

    @Autowired
    @Qualifier("userDAO")
    public void set_userDAO(SuperDAO<User> _userDAO) {
        this._userDAO = _userDAO;
    }
    @Autowired
    @Qualifier("roleDAO")
    public void set_roleDAO(SuperDAO<Role> _roleDAO) {
        this._roleDAO = _roleDAO;
    }
    @Autowired
    @Qualifier("courseDAO")
    public void set_courseDAO(SuperDAO<Course> _courseDAO) {
        this._courseDAO = _courseDAO;
    }
    @PostConstruct
    public void init() {
        userDAO = _userDAO;
        roleDAO = _roleDAO;
        courseDAO = _courseDAO;
    }

    public static UserRole packUserRole(ResultSet resultSet) throws SQLException {
        try {
            Integer id = resultSet.getInt("id");
            Integer user_id = resultSet.getInt("user_id");
            Integer role_id = resultSet.getInt("role_id");

            UserRole userRole = new UserRole();
            User user = userDAO.get(user_id);
            Role role = roleDAO.get(role_id);
            userRole.setId(id);
            userRole.setUser(user);
            userRole.setRole(role);
            return userRole;
        } catch ( DAOException e) {
            logger.error(e);
        }
        return null;
    }

    public static User packUser(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String surname = resultSet.getString("surname");
        String patronymic = resultSet.getString("patronymic");
        Date birthday = resultSet.getDate("birthday");
        String sex = resultSet.getString("sex");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setBirthday(birthday);
        user.setSex(sex);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    private static List<Lesson> packCourseLessons(Course course, Connection conn) {

        try (PreparedStatement preparedStatement = conn.prepareStatement(GET_COURSE_LESSONS_SQL)) {
            preparedStatement.setInt(1, course.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Lesson> lessons = new ArrayList<>();
            while (resultSet.next()) {
                Lesson lesson = packLesson(resultSet, course);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    public static Course packCourse(ResultSet resultSet, Connection conn) throws SQLException {
        try {
            Integer id = resultSet.getInt("id");
            Integer author_user_id = resultSet.getInt("author_user_id");
            String namec = resultSet.getString("name");
            Double duration = resultSet.getDouble("duration");

            Course course = new Course();
            User author = userDAO.get(author_user_id);
            course.setAuthor(author);
            course.setId(id);
            course.setName(namec);
            course.setDuration(duration);
            List<Lesson> lessons = packCourseLessons(course, conn);
            course.setLessons(lessons);

            return course;
        } catch ( DAOException e) {
            logger.error(e);
        }
        return null;
    }

    private static Lesson packLesson(ResultSet resultSet, Course course) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer orderNum = resultSet.getInt("ordernum");
        String theme = resultSet.getString("theme");
        Double duration = resultSet.getDouble("duration");
        String content = resultSet.getString("content");

        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setCourse(course);
        lesson.setOrderNum(orderNum);
        lesson.setTheme(theme);
        lesson.setDuration(duration);
        lesson.setContent(content);

        return lesson;
    }

    public static Lesson packLesson(ResultSet resultSet) throws SQLException {
        try {
            Integer id = resultSet.getInt("id");
            Integer course_id = resultSet.getInt("course_id");
            Integer orderNum = resultSet.getInt("ordernum");
            String theme = resultSet.getString("theme");
            Double duration = resultSet.getDouble("duration");
            String content = resultSet.getString("content");

            Lesson lesson = new Lesson();
            Course course = courseDAO.get(course_id);
            lesson.setId(id);
            lesson.setCourse(course);
            lesson.setOrderNum(orderNum);
            lesson.setTheme(theme);
            lesson.setDuration(duration);
            lesson.setContent(content);

            return lesson;
        } catch ( DAOException e) {
            logger.error(e);
        }
        return null;
    }

    public static Role packRole(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String roleName = resultSet.getString("role");
        String description = resultSet.getString("description");

        Role role = new Role();
        role.setId(id);
        role.setRole(roleName);
        role.setDescription(description);
        return role;
    }
}
