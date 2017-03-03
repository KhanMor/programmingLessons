package models.daoimpl.jdbcimpl;

import common.exceptions.DAOException;
import models.daoimpl.jdbcimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 17.02.2017.
 * CRUD пользователей через JDBC
 */
//@Repository("userDAO")
public class UserDAOImpl implements SuperDAO<User> {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    private static final String LIST_SQL;
    private static final String GET_SQL;
    private static final String INSERT_SQL;
    private static final String UPDATE_SQL;
    private static final String DELETE_SQL;
    private static final String DELETE_ALL_SQL;
    private static final String REFRESH_INCREMENT_SQL;

    static {
        GET_SQL = "select * from user where id=?";
        LIST_SQL = "select * from user";
        INSERT_SQL = "insert into user (firstname, surname, patronymic, birthday, sex, email, password)" +
                " values (?, ?, ?, ?, ?, ?, ?)";
        UPDATE_SQL = "update user set firstname = ?, surname = ?, patronymic = ?, birthday = ?, sex = ?," +
                " email = ?, password = ? where id = ?";
        DELETE_SQL = "delete from user where id=?";
        DELETE_ALL_SQL = "delete from user";
        REFRESH_INCREMENT_SQL = "ALTER TABLE user AUTO_INCREMENT = 1;";
    }


    private DataSource dataSource;

    public UserDAOImpl() {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> list() throws DAOException {
        try(Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(LIST_SQL);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = EntityBoxer.packUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public User get(Integer id) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User student;
                student = EntityBoxer.packUser(resultSet);
                return student;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return null;
    }

    @Override
    public void insert(User user) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPatronymic());
            preparedStatement.setDate(4, user.getBirthday());
            preparedStatement.setString(5, user.getSex());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPassword());
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No user inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID.");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SQL))
        {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPatronymic());
            preparedStatement.setDate(4, user.getBirthday());
            preparedStatement.setString(5, user.getSex());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setInt(8, user.getId());
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows == 0) {
                throw new SQLException("No user updated");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DAOException {
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
    public void deleteAll() throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ALL_SQL);
            PreparedStatement preparedStatement2 = conn.prepareStatement(REFRESH_INCREMENT_SQL)
        ) {
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }
}
