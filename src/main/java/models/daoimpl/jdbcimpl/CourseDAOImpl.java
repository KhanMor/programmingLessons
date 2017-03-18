package models.daoimpl.jdbcimpl;

import common.exceptions.DAOException;
import models.daoimpl.jdbcimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.entity.Course;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 17.02.2017.
 * CRUD с курсами через JDBC
 */
//@Repository("courseDAO")
public class CourseDAOImpl implements SuperDAO<Course> {
    private static final Logger logger = Logger.getLogger(CourseDAOImpl.class);
    private static final String LIST_SQL;
    private static final String GET_SQL;
    private static final String INSERT_SQL;
    private static final String DELETE_SQL;
    private static final String DELETE_ALL_SQL;
    private static final String REFRESH_INCREMENT_SQL;

    static {
        LIST_SQL = "select * from course";
        GET_SQL = "select * from course where id=?";
        INSERT_SQL = "insert into course (author_user_id, name, duration, price, certified) values (?, ?, ?, ?, ?)";
        DELETE_SQL = "delete from course where id=?";
        DELETE_ALL_SQL = "delete from course";
        REFRESH_INCREMENT_SQL = "ALTER TABLE course AUTO_INCREMENT = 1;";
    }

    private DataSource dataSource;

    public CourseDAOImpl() {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Course> list() throws DAOException {
        try(Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(LIST_SQL);
            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                Course course = EntityBoxer.packCourse(resultSet, conn);
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public Course get(Integer id) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return EntityBoxer.packCourse(resultSet, conn);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return null;
    }

    private void setPreparedStatementParams(PreparedStatement preparedStatement, Course course) throws SQLException {
        preparedStatement.setInt(1, course.getAuthor().getId());
        preparedStatement.setString(2, course.getName());
        preparedStatement.setDouble(3, course.getDuration());
    }

    @Override
    public void insert(Course course) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
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
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Course course) throws DAOException {

    }

    @Override
    public void delete(Integer id)  throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SQL)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteAll()  throws DAOException {
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
