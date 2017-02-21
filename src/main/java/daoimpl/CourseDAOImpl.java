package daoimpl;

import boxer.EntityBoxer;
import dao.SuperDAO;
import models.Course;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 17.02.2017.
 */
public class CourseDAOImpl implements SuperDAO<Course> {
    private final Connection conn;
    private static final Logger logger = Logger.getLogger(CourseDAOImpl.class);

    public CourseDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Course> list() {
        String sql = "select * from course";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                Course course = EntityBoxer.packCourse(resultSet, conn);
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Course get(Integer id) {
        String sql = "select * from course where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = EntityBoxer.packCourse(resultSet, conn);
                return course;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private void setPreparedStatementParams(PreparedStatement preparedStatement, Course course) throws SQLException {
        preparedStatement.setInt(1, course.getAuthor().getId());
        preparedStatement.setString(2, course.getName());
        preparedStatement.setDouble(3, course.getDuration());
        preparedStatement.setDouble(4, course.getPrice());
        preparedStatement.setBoolean(5, course.isCertified());
    }

    @Override
    public void insert(Course course) {
        String sql = "insert into course (author_user_id, name, duration, price, certified) values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementParams(preparedStatement, course);
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No course inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating course failed, no ID.");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(Course course) {
        String sql = "delete from course where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, course.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from course";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        sql = "ALTER TABLE course AUTO_INCREMENT = 1;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
