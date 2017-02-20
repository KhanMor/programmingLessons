package boxer;

import dao.*;
import daoimpl.*;
import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 17.02.2017.
 */
public final class EntityBoxer {
    public static List<UserRole> packUserRole(User user, Connection conn) throws SQLException {
        String sql = "select * from userrole where user_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserRole> userRoles = new ArrayList<>();
            SuperDAO<Role> roleDAO = new RoleDAOImpl(conn);
            while (resultSet.next()) {
                UserRole userRole = new UserRole();
                userRole.setId(resultSet.getInt("id"));
                userRole.setUser(user);
                Role role = roleDAO.get(resultSet.getInt("role_id"));
                userRole.setRole(role);
                userRoles.add(userRole);
            }
            return userRoles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User packUser(ResultSet resultSet, Connection conn) throws SQLException {
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
        user.setUserRoles(packUserRole(user, conn));

        return user;
    }

    public static List<Lesson> packCourseLessons(Course course, Connection conn) {
        String sql = "select * from lesson where course_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, course.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Lesson> lessons = new ArrayList<>();
            while (resultSet.next()) {
                Lesson lesson = packLesson(resultSet, conn, course);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Course packCourse(ResultSet resultSet, Connection conn) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer author_user_id = resultSet.getInt("author_user_id");
        String namec = resultSet.getString("name");
        Double duration = resultSet.getDouble("duration");
        Double price = resultSet.getDouble("price");
        Boolean certified = resultSet.getBoolean("certified");

        Course course = new Course();
        SuperDAO<User> userDAO = new UserDAOImpl(conn);
        User author = userDAO.get(author_user_id);
        course.setAuthor(author);
        course.setId(id);
        course.setName(namec);
        course.setPrice(price);
        course.setCertified(certified);
        course.setDuration(duration);
        List<Lesson> lessons = packCourseLessons(course, conn);
        course.setLessons(lessons);

        return course;
    }

    public static Lesson packLesson(ResultSet resultSet, Connection conn, Course course) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer orderNum = resultSet.getInt("ordernum");
        String theme = resultSet.getString("theme");
        Double duration = resultSet.getDouble("duration");
        String content = resultSet.getString("content");
        Integer scoreToPass = resultSet.getInt("scoretopass");

        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setCourse(course);
        lesson.setOrderNum(orderNum);
        lesson.setTheme(theme);
        lesson.setDuration(duration);
        lesson.setContent(content);
        lesson.setScoreToPass(scoreToPass);
        List<LessonTest> lessonTests = packLessonLessonTests(lesson, conn);
        lesson.setLessonTests(lessonTests);

        return lesson;
    }

    public static Lesson packLesson(ResultSet resultSet, Connection conn) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer course_id = resultSet.getInt("course_id");
        Integer orderNum = resultSet.getInt("ordernum");
        String theme = resultSet.getString("theme");
        Double duration = resultSet.getDouble("duration");
        String content = resultSet.getString("content");
        Integer scoreToPass = resultSet.getInt("scoretopass");

        Lesson lesson = new Lesson();
        SuperDAO<Course> courseDAO = new CourseDAOImpl(conn);
        Course course = courseDAO.get(course_id);
        lesson.setId(id);
        lesson.setCourse(course);
        lesson.setOrderNum(orderNum);
        lesson.setTheme(theme);
        lesson.setDuration(duration);
        lesson.setContent(content);
        lesson.setScoreToPass(scoreToPass);
        List<LessonTest> lessonTests = packLessonLessonTests(lesson, conn);
        lesson.setLessonTests(lessonTests);

        return lesson;
    }

    public static List<LessonTest> packLessonLessonTests(Lesson lesson, Connection conn) {
        String sql = "select * from lessontest where lesson_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, lesson.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<LessonTest> lessonTests = new ArrayList<>();
            while (resultSet.next()) {
                LessonTest lessonTest = packLessonTest(resultSet, lesson);
                lessonTests.add(lessonTest);
            }
            return lessonTests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LessonTest packLessonTest(ResultSet resultSet, Lesson lesson) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer orderNum = resultSet.getInt("ordernum");
        String question = resultSet.getString("question");
        String answer = resultSet.getString("answer");
        Integer points = resultSet.getInt("points");

        LessonTest lessonTest = new LessonTest();
        lessonTest.setId(id);
        lessonTest.setLesson(lesson);
        lessonTest.setOrderNum(orderNum);
        lessonTest.setQuestion(question);
        lessonTest.setAnswer(answer);
        lessonTest.setPoints(points);

        return lessonTest;
    }

    public static LessonTest packLessonTest(ResultSet resultSet, Connection conn) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer lesson_id = resultSet.getInt("lesson_id");
        Integer orderNum = resultSet.getInt("ordernum");
        String question = resultSet.getString("question");
        String answer = resultSet.getString("answer");
        Integer points = resultSet.getInt("points");

        LessonTest lessonTest = new LessonTest();
        SuperDAO<Lesson> lessonDAO = new LessonDAOImpl(conn);
        Lesson lesson = lessonDAO.get(lesson_id);
        lessonTest.setId(id);
        lessonTest.setLesson(lesson);
        lessonTest.setOrderNum(orderNum);
        lessonTest.setQuestion(question);
        lessonTest.setAnswer(answer);
        lessonTest.setPoints(points);

        return lessonTest;
    }

    public static LessonTestResult packLessonTestResult(ResultSet resultSet, Connection conn) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer lessonTest_id = resultSet.getInt("lessontest_id");
        Integer user_id = resultSet.getInt("user_id");
        Timestamp testDateTime = resultSet.getTimestamp("testdatetime");
        Integer score = resultSet.getInt("score");

        LessonTestResult lessonTestResult = new LessonTestResult();
        SuperDAO<LessonTest> lessonTestDAO = new LessonTestDAOImpl(conn);
        LessonTest lessonTest = lessonTestDAO.get(lessonTest_id);
        SuperDAO<User> userDAO = new UserDAOImpl(conn);
        User user = userDAO.get(user_id);

        lessonTestResult.setId(id);
        lessonTestResult.setLessonTest(lessonTest);
        lessonTestResult.setUser(user);
        lessonTestResult.setTestDateTime(testDateTime);
        lessonTestResult.setScore(score);
        return lessonTestResult;
    }

    public static Role packRole(ResultSet resultSet, Connection conn) throws SQLException {
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
