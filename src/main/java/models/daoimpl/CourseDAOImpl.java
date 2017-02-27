package models.daoimpl;

import common.exceptions.DAOException;
import models.connector.DataSourceMySQL;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.Course;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 17.02.2017.
 */
public class CourseDAOImpl implements SuperDAO<Course> {
    //private final Connection conn;
    private static final Logger logger = Logger.getLogger(CourseDAOImpl.class);
    private static final String LIST_SQL = "select * from course";
    private static final String GET_SQL = "select * from course where id=?";
    private static final String INSERT_SQL = "insert into course (author_user_id, name, duration, price, certified) values (?, ?, ?, ?, ?)";
    private static final String DELETE_SQL = "delete from course where id=?";
    private static final String DELETE_ALL_SQL = "delete from course";
    private static final String REFRESH_INCREMENT_SQL = "ALTER TABLE course AUTO_INCREMENT = 1;";
    private DataSource dataSource = DataSourceMySQL.getInstance().getDataSource();

    public CourseDAOImpl() {
        /*DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();*/
    }

    @Override
    public List<Course> list()  throws DAOException {
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
            throw new DAOException();
        }
    }

    @Override
    public Course get(Integer id)  throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = EntityBoxer.packCourse(resultSet, conn);
                return course;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
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
            throw new DAOException();
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
            throw new DAOException();
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
