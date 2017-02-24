package models.daoimpl;

import models.connector.DatabaseConnector;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.Lesson;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
public class LessonDAOImpl implements SuperDAO<Lesson> {
    private final Connection conn;
    private static final Logger logger = Logger.getLogger(LessonDAOImpl.class);

    public LessonDAOImpl() {
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();
    }

    @Override
    public List<Lesson> list() {
        String sql = "select * from lesson";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Lesson> lessons = new ArrayList<>();
            while (resultSet.next()) {
                Lesson lesson = EntityBoxer.packLesson(resultSet, conn);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public Lesson get(Integer id) {
        String sql = "select * from lesson where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = EntityBoxer.packLesson(resultSet, conn);
                return lesson;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    private void setPreparedStatementParams(PreparedStatement preparedStatement, Lesson lesson) throws SQLException {
        preparedStatement.setInt(1, lesson.getCourse().getId());
        preparedStatement.setInt(2, lesson.getOrderNum());
        preparedStatement.setString(3, lesson.getTheme());
        preparedStatement.setDouble(4, lesson.getDuration());
        preparedStatement.setString(5, lesson.getContent());
        preparedStatement.setInt(6, lesson.getScoreToPass());
    }

    @Override
    public void insert(Lesson lesson) {
        String sql = "insert into lesson (course_id, ordernum, theme, duration, content, scoretopass)" +
                " values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementParams(preparedStatement, lesson);
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No lesson inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lesson.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating lesson failed, no ID.");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void update(Lesson lesson) {

    }

    @Override
    public void delete(Lesson lesson) {
        String sql = "delete from lesson where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, lesson.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from lesson";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        sql = "ALTER TABLE lesson AUTO_INCREMENT = 1;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
