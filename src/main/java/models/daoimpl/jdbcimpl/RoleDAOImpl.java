package models.daoimpl.jdbcimpl;

import common.exceptions.DAOException;
import models.daoimpl.jdbcimpl.boxer.EntityBoxer;
import models.dao.SuperDAO;
import models.entity.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 * CRUD с ролями через JDBC
 */
//@Repository("roleDAO")
public class RoleDAOImpl implements SuperDAO<Role> {
    private static final Logger logger = Logger.getLogger(RoleDAOImpl.class);
    private static final String LIST_SQL;
    private static final String GET_SQL;
    private static final String INSERT_SQL;
    private static final String DELETE_SQL;
    private static final String DELETE_ALL_SQL;
    private static final String REFRESH_INCREMENT_SQL;

    static {
        LIST_SQL = "select * from role";
        GET_SQL = "select * from role where id=?";
        INSERT_SQL = "insert into role (role, description) values (?, ?)";
        DELETE_SQL = "delete from role where id=?";
        DELETE_ALL_SQL = "delete from role";
        REFRESH_INCREMENT_SQL = "ALTER TABLE role AUTO_INCREMENT = 1;";
    }

    private DataSource dataSource;

    public RoleDAOImpl() {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Role> list() throws DAOException {
        try(Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(LIST_SQL);
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                Role role = EntityBoxer.packRole(resultSet);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public Role get(Integer id) throws DAOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SQL)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return EntityBoxer.packRole(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
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
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Role role) throws DAOException {

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
