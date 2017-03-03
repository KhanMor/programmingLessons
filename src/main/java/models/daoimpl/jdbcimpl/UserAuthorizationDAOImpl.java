package models.daoimpl.jdbcimpl;

import common.exceptions.DAOException;
import models.dao.UserAuthorizationDAO;
import models.daoimpl.jdbcimpl.boxer.EntityBoxer;
import models.pojo.Role;
import models.pojo.User;
import models.pojo.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 02.03.2017.
 * Авторизация пользователя через JDBC
 */
//@Repository("userAuthorizationDAO")
public class UserAuthorizationDAOImpl implements UserAuthorizationDAO {
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAO.class);
    private final static String LOGIN_SQL;
    private final static String ROLE_BY_NAME_SQL;
    private final static String DELETE_USER_ALL_ROLES_SQL;
    private final static String SELECT_USER_ALL_ROLES_SQL;
    private final static String FIND_USER_ROLE_SQL;

    static {
        SELECT_USER_ALL_ROLES_SQL = "select * from userrole where user_id = ?";
        DELETE_USER_ALL_ROLES_SQL = "delete from userrole where user_id = ?";
        ROLE_BY_NAME_SQL = "select * from role where role = ?";
        LOGIN_SQL = "select * from user where email = ? and password = ?";
        FIND_USER_ROLE_SQL = "select distinct a.* from role a" +
                                " inner join userrole b on a.id= b.role_id" +
                                " where b.user_id = ? and b.role_id = ?";
    }

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(LOGIN_SQL)
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = EntityBoxer.packUser(resultSet);
                logger.trace("User found " + user);
                return user;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return null;
    }

    @Override
    public Role findRoleByName(String roleName) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(ROLE_BY_NAME_SQL))
        {
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Role role;
                role = EntityBoxer.packRole(resultSet);
                return role;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return null;
    }

    @Override
    public void deleteUserAllRoles(Integer user_id) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_ALL_ROLES_SQL)
        ) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public List<UserRole> getUserAllRoles(User user) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER_ALL_ROLES_SQL)
        ) {
            Integer user_id = user.getId();
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserRole> userRoles = new ArrayList<>();
            while(resultSet.next()) {
                UserRole userRole = EntityBoxer.packUserRole(resultSet);
                userRoles.add(userRole);
            }
            return userRoles;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public Role findUserRole(User user, Role role) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_USER_ROLE_SQL))
        {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, role.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Role findedRole;
                findedRole = EntityBoxer.packRole(resultSet);
                return findedRole;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) throws DAOException {
        return null;
    }
}
