package models.daoimpl.jdbcimpl;

import common.exceptions.DAOException;
import models.daoimpl.jdbcimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.entity.Lesson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 * CRUD с уроками через JDBC
 */
//@Repository("lessonDAO")
public class LessonDAOImpl implements SuperDAO<Lesson> {
    private static final Logger logger = Logger.getLogger(LessonDAOImpl.class);
    private static final String LIST_SQL;
    private static final String GET_SQL;
    private static final String INSERT_SQL;
    private static final String DELETE_SQL;
    private static final String DELETE_ALL_SQL;
    private static final String REFRESH_INCREMENT_SQL;

    static {
        LIST_SQL = "select * from lesson";
        GET_SQL = "select * from lesson where id=?";
        INSERT_SQL = "insert into lesson (course_id, ordernum, theme, duration, content, scoretopass)" +
                " values (?, ?, ?, ?, ?, ?)";
        DELETE_SQL = "delete from lesson where id=?";
        DELETE_ALL_SQL = "delete from lesson";
        REFRESH_INCREMENT_SQL = "ALTER TABLE lesson AUTO_INCREMENT = 1;";
    }

    private DataSource dataSource;

    public LessonDAOImpl() {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Lesson> list() throws DAOException {
        try(Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(LIST_SQL);
            List<Lesson> lessons = new ArrayList<>();
            while (resultSet.next()) {
                Lesson lesson = EntityBoxer.packLesson(resultSet);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public Lesson get(Integer id) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return EntityBoxer.packLesson(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return null;
    }

    private void setPreparedStatementParams(PreparedStatement preparedStatement, Lesson lesson) throws SQLException {
        preparedStatement.setInt(1, lesson.getCourse().getId());
        preparedStatement.setInt(2, lesson.getOrderNum());
        preparedStatement.setString(3, lesson.getTheme());
        preparedStatement.setDouble(4, lesson.getDuration());
        preparedStatement.setString(5, lesson.getContent());
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
            throw new DAOException(e);
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
