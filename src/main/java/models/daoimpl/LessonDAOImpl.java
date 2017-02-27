package models.daoimpl;

import common.exceptions.DAOException;
import models.connector.DataSourceMySQL;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.Lesson;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
public class LessonDAOImpl implements SuperDAO<Lesson> {
    //private final Connection conn;
    private static final Logger logger = Logger.getLogger(LessonDAOImpl.class);
    private static final String LIST_SQL = "select * from lesson";
    private static final String GET_SQL = "select * from lesson where id=?";
    private static final String INSERT_SQL = "insert into lesson (course_id, ordernum, theme, duration, content, scoretopass)" +
                                             " values (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_SQL = "delete from lesson where id=?";
    private static final String DELETE_ALL_SQL = "delete from lesson";
    private static final String REFRESH_INCREMENT_SQL = "ALTER TABLE lesson AUTO_INCREMENT = 1;";
    private DataSource dataSource = DataSourceMySQL.getInstance().getDataSource();

    public LessonDAOImpl() {
        /*DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();*/
    }

    @Override
    public List<Lesson> list() {
        try(Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(LIST_SQL);
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
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
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
    public void insert(Lesson lesson) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
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
            throw new DAOException();
        }
    }

    @Override
    public void update(Lesson lesson) {

    }

    @Override
    public void delete(Integer id) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SQL)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void deleteAll() {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ALL_SQL);
            PreparedStatement preparedStatement2 = conn.prepareStatement(REFRESH_INCREMENT_SQL)
        ) {
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
