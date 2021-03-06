package models.daoimpl;

import common.exceptions.DAOException;
import models.connector.DataSourceMySQL;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.UserRole;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
public class UserRoleDAOImpl implements SuperDAO<UserRole> {
    //private final Connection conn;
    private static final Logger logger = Logger.getLogger(UserRoleDAOImpl.class);
    private static final String LIST_SQL = "select * from userrole";
    private static final String GET_SQL = "select * from userRole where id=?";
    private static final String INSERT_SQL = "insert into userrole (user_id, role_id) values (?, ?)";
    private static final String DELETE_ALL_SQL = "delete from userrole";
    private static final String REFRESH_INCREMENT_SQL = "ALTER TABLE userrole AUTO_INCREMENT = 1;";
    private DataSource dataSource = DataSourceMySQL.getInstance().getDataSource();

    public UserRoleDAOImpl() {
        /*DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();*/
    }

    @Override
    public List<UserRole> list() {
        try(Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(LIST_SQL);
            List<UserRole> userRoles = new ArrayList<>();
            while (resultSet.next()) {
                UserRole userRole = EntityBoxer.packUserRole(resultSet, conn);
                userRoles.add(userRole);
            }
            return userRoles;
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public UserRole get(Integer id) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserRole course = EntityBoxer.packUserRole(resultSet, conn);
                return course;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public void insert(UserRole userRole) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setInt(1, userRole.getUser().getId());
            preparedStatement.setInt(2, userRole.getRole().getId());
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No user's role inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userRole.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user's role failed, no ID.");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
        }
    }

    @Override
    public void update(UserRole userRole) {

    }

    @Override
    public void delete(Integer id) {

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
            e.printStackTrace();
        }
    }
}
