package daoimpl;

import boxer.EntityBoxer;
import dao.UserRoleDAO;
import models.Role;
import models.User;

import java.sql.*;

/**
 * Created by Mordr on 18.02.2017.
 */
public class UserRoleDAOImpl implements UserRoleDAO {
    private final Connection conn;

    public UserRoleDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addUserRole(User user, Role role) {
        String sql = "insert into userrole (user_id, role_id) values (?, ?)";
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, role.getId());
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No user's role inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user's role failed, no ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserRole(User user, Role role) {
        String sql = "delete from userrole where user_id = ? and role_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, role.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
    }

    @Override
    public Role getRoleByName(String rolename) {
        String sql = "select * from role where role = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, rolename);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = EntityBoxer.packRole(resultSet, conn);
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
