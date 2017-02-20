package daoimpl;

import boxer.EntityBoxer;
import dao.SuperDAO;
import dao.UserRoleDAO;
import models.Role;
import models.User;
import models.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 17.02.2017.
 */
public class UserDAOImpl implements SuperDAO<User> {
    private final Connection conn;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    private void setPreparedStatementParams(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getPatronymic());
        preparedStatement.setDate(4, user.getBirthday());
        preparedStatement.setString(5, user.getSex());
        preparedStatement.setString(6, user.getEmail());
        preparedStatement.setString(7, user.getPassword());
    }

    @Override
    public List<User> list() {
        String sql = "select * from user";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = EntityBoxer.packUser(resultSet, conn);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User get(Integer id) {
        String sql = "select * from user where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User student = EntityBoxer.packUser(resultSet, conn);
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(User user) {
        String sql = "insert into user (firstname, surname, patronymic, birthday, sex, email, password)" +
                " values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementParams(preparedStatement, user);
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No user inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    if (user.getId() != null) {
                        List<UserRole> userRoles = user.getUserRoles();
                        UserRoleDAO userRoleDAO = new UserRoleDAOImpl(conn);
                        SuperDAO roleDAO = new RoleDAOImpl(conn);
                        for (UserRole userRole : userRoles) {
                            Role role = userRoleDAO.getRoleByName(userRole.getRole().getRole());
                            if (role == null) {
                                roleDAO.insert(role);
                            }
                            userRoleDAO.addUserRole(user, role);
                        }
                    }
                } else {
                    throw new SQLException("Creating user failed, no ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

    }

    private void deleteUserRoles(User user) {
        String sql = "delete from userrole where user_id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        deleteUserRoles(user);
        String sql = "delete from user where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from user";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "ALTER TABLE user AUTO_INCREMENT = 1;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
