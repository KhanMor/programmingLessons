package models.daoimpl;

import common.exceptions.DAOException;
import models.connector.DataSourceMySQL;
import models.daoimpl.boxer.EntityBoxer;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 23.02.2017.
 */
public class UserAuthorizationDAO {
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAO.class);
    private final static String LOGIN_SQL = "select * from user where email = ? and password = ?";
    private final static String ROLE_BY_NAME_SQL = "select * from role where role = ?";
    private final static String DELETE_USER_ALL_ROLES_SQL = "delete from userrole where user_id = ?";
    private final static String SELECT_USER_ALL_ROLES_SQL = "select * from userrole where user_id = ?";

    private DataSource dataSource = DataSourceMySQL.getInstance().getDataSource();

    public UserAuthorizationDAO() {
        /*DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();*/
    }

    public User findUserByEmailAndPassword(String email, String password) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(LOGIN_SQL)
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = EntityBoxer.packUser(resultSet, conn);
                logger.trace("User found " + user);
                return user;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
        }
        return null;
    }

    public Role findRoleByName(String roleName) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(ROLE_BY_NAME_SQL))
        {
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Role role = EntityBoxer.packRole(resultSet, conn);
                logger.trace(role);
                return role;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
        }
        return null;
    }

    public void deleteUserAllRoles(Integer user_id) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_ALL_ROLES_SQL)
        ) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
        }
    }

    public List<UserRole> getUserAllRoles(User user) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER_ALL_ROLES_SQL)
        ) {
            Integer user_id = user.getId();
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserRole> userRoles = new ArrayList<>();
            while(resultSet.next()) {
                UserRole userRole = EntityBoxer.packUserRole(resultSet, conn);
                userRoles.add(userRole);
            }
            return userRoles;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
        }
    }
}
