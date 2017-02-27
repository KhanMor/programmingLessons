package models.daoimpl;

import common.exceptions.DAOException;
import models.connector.DatabaseConnector;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.LessonTestResult;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
@Deprecated
public class LessonTestResultDAOImpl implements SuperDAO<LessonTestResult> {
    private final Connection conn;
    private static final Logger logger = Logger.getLogger(LessonTestDAOImpl.class);

    public LessonTestResultDAOImpl() {
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();
    }


    @Override
    public List<LessonTestResult> list() {
        String sql = "select * from lessontestresult";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<LessonTestResult> lessonTestsResults = new ArrayList<>();
            while (resultSet.next()) {
                LessonTestResult lessonTestResult = EntityBoxer.packLessonTestResult(resultSet, conn);
                lessonTestsResults.add(lessonTestResult);
            }
            return lessonTestsResults;
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public LessonTestResult get(Integer id) {
        String sql = "select * from lessontestresult where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LessonTestResult lessonTestResult = EntityBoxer.packLessonTestResult(resultSet, conn);
                return lessonTestResult;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    private void setPreparedStatementParams(PreparedStatement preparedStatement,
                                            LessonTestResult lessonTestResult) throws SQLException {
        preparedStatement.setInt(1, lessonTestResult.getLessonTest().getId());
        preparedStatement.setInt(2, lessonTestResult.getUser().getId());
        preparedStatement.setTimestamp(3, lessonTestResult.getTestDateTime());
        preparedStatement.setInt(4, lessonTestResult.getScore());
    }

    @Override
    public void insert(LessonTestResult lessonTestResult) throws DAOException {
        String sql = "insert into lessontestresult (lessontest_id, user_id, testdatetime, score) values (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementParams(preparedStatement, lessonTestResult);
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No test's result inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lessonTestResult.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating test's result failed, no ID.");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
        }
    }

    @Override
    public void update(LessonTestResult lessonTestResult) {

    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from lessontestresult where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from lessontestresult";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        sql = "ALTER TABLE lessontestresult AUTO_INCREMENT = 1;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
