package models.daoimpl;

import common.exceptions.DAOException;
import models.connector.DatabaseConnector;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.LessonTest;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
@Deprecated
public class LessonTestDAOImpl implements SuperDAO<LessonTest> {
    private final Connection conn;
    private static final Logger logger = Logger.getLogger(LessonTestDAOImpl.class);

    public LessonTestDAOImpl() {
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();
    }

    @Override
    public List<LessonTest> list() {
        String sql = "select * from lessonTest";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<LessonTest> lessonTests = new ArrayList<>();
            while (resultSet.next()) {
                LessonTest lessonTest = EntityBoxer.packLessonTest(resultSet, conn);
                lessonTests.add(lessonTest);
            }
            return lessonTests;
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public LessonTest get(Integer id) {
        String sql = "select * from lessonTest where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LessonTest lessonTest = EntityBoxer.packLessonTest(resultSet, conn);
                return lessonTest;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    private void setPreparedStatementParams(PreparedStatement preparedStatement, LessonTest lessonTest) throws SQLException {
        preparedStatement.setInt(1, lessonTest.getLesson().getId());
        preparedStatement.setInt(2, lessonTest.getOrderNum());
        preparedStatement.setString(3, lessonTest.getQuestion());
        preparedStatement.setString(4, lessonTest.getAnswer());
        preparedStatement.setInt(5, lessonTest.getPoints());
    }

    @Override
    public void insert(LessonTest lessonTest) throws DAOException {
        String sql = "insert into lessontest (lesson_id, ordernum, question, answer, points)" +
                " values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementParams(preparedStatement, lessonTest);
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No lesson's test inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lessonTest.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating lesson's test failed, no ID.");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
        }
    }

    @Override
    public void update(LessonTest lessonTest) {

    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from lessontest where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from lessontest";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        sql = "ALTER TABLE lessontest AUTO_INCREMENT = 1;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
