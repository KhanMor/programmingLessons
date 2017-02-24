package models.daoimpl;

import models.connector.DatabaseConnector;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.Role;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
public class RoleDAOImpl implements SuperDAO<Role> {
    private final Connection conn;
    private static final Logger logger = Logger.getLogger(RoleDAOImpl.class);

    public RoleDAOImpl() {
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();
    }

    @Override
    public List<Role> list() {
        String sql = "select * from role";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                Role role = EntityBoxer.packRole(resultSet, conn);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public Role get(Integer id) {
        String sql = "select * from role where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = EntityBoxer.packRole(resultSet, conn);
                return role;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    private void setPreparedStatementParams(PreparedStatement preparedStatement, Role role) throws SQLException {
        preparedStatement.setString(1, role.getRole());
        preparedStatement.setString(2, role.getDescription());
    }

    @Override
    public void insert(Role role) {
        String sql = "insert into role (role, description)" +
                " values (?, ?)";
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setPreparedStatementParams(preparedStatement, role);
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows == 0) {
                throw new SQLException("No role inserted");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating role failed, no ID.");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(Role role) {
        String sql = "delete from role where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, role.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from role";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
        sql = "ALTER TABLE role AUTO_INCREMENT = 1;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}