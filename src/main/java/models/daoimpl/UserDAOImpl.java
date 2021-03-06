package models.daoimpl;

import common.exceptions.DAOException;
import models.connector.DataSourceMySQL;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.User;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 17.02.2017.
 */
public class UserDAOImpl implements SuperDAO<User> {
    //private final Connection conn;
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    private static final String LIST_SQL = "select * from user";
    private static final String GET_SQL = "select * from user where id=?";
    private static final String INSERT_SQL = "insert into user (firstname, surname, patronymic, birthday, sex, email, password)" +
                                             " values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "update user set firstname = ?, surname = ?, patronymic = ?, birthday = ?, sex = ?," +
                                             " email = ?, password = ? where id = ?";
    private static final String DELETE_SQL = "delete from user where id=?";
    private static final String DELETE_ALL_SQL = "delete from user";
    private static final String REFRESH_INCREMENT_SQL = "ALTER TABLE user AUTO_INCREMENT = 1;";

    private DataSource dataSource = DataSourceMySQL.getInstance().getDataSource();

    public UserDAOImpl() {
       /* DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();*/
    }

    @Override
    public List<User> list() {
        try(Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(LIST_SQL);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = EntityBoxer.packUser(resultSet, conn);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public User get(Integer id) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User student = EntityBoxer.packUser(resultSet, conn);
                return student;
            }
        } catch (SQLException e) {
            logger.error(e);
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
            throw new DAOException();
        }
    }

    @Override
    public void update(User user) {
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
        }
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
