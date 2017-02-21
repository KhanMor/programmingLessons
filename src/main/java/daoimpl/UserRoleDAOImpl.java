package daoimpl;

import boxer.EntityBoxer;
import dao.SuperDAO;
import models.UserRole;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
public class UserRoleDAOImpl implements SuperDAO<UserRole> {
    private final Connection conn;
    private static final Logger logger = Logger.getLogger(UserRoleDAOImpl.class);

    public UserRoleDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<UserRole> list() {
        String sql = "select * from userrole";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<UserRole> userRoles = new ArrayList<>();
            while (resultSet.next()) {
                UserRole userRole = EntityBoxer.packUserRole(resultSet, conn);
                userRoles.add(userRole);
            }
            return userRoles;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public UserRole get(Integer id) {
        String sql = "select * from userRole where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserRole course = EntityBoxer.packUserRole(resultSet, conn);
                return course;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void insert(UserRole userRole) {
        String sql = "insert into userrole (user_id, role_id) values (?, ?)";
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            logger.error(e.getMessage());
        }
    }

    @Override
    public void update(UserRole userRole) {

    }

    @Override
    public void delete(UserRole userRole) {

    }

    @Override
    public void deleteAll() {
        String sql = "delete from userrole";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "ALTER TABLE userrole AUTO_INCREMENT = 1;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
