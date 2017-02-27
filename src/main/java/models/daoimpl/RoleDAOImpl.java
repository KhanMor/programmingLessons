package models.daoimpl;

import common.exceptions.DAOException;
import models.connector.DataSourceMySQL;
import models.daoimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.pojo.Role;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
public class RoleDAOImpl implements SuperDAO<Role> {
    //private final Connection conn;
    private static final Logger logger = Logger.getLogger(RoleDAOImpl.class);
    private static final String LIST_SQL = "select * from role";
    private static final String GET_SQL = "select * from role where id=?";
    private static final String INSERT_SQL = "insert into role (role, description) values (?, ?)";
    private static final String DELETE_SQL = "delete from role where id=?";
    private static final String DELETE_ALL_SQL = "delete from role";
    private static final String REFRESH_INCREMENT_SQL = "ALTER TABLE role AUTO_INCREMENT = 1;";
    private DataSource dataSource = DataSourceMySQL.getInstance().getDataSource();

    public RoleDAOImpl() {
        /*DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();*/
    }

    @Override
    public List<Role> list() {
        try(Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(LIST_SQL);
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
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
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
    public void insert(Role role) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
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
            throw new DAOException();
        }
    }

    @Override
    public void update(Role role) {

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
