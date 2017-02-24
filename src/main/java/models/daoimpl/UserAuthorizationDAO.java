package models.daoimpl;

import exceptions.DAOException;
import models.connector.DatabaseConnector;
import models.daoimpl.boxer.EntityBoxer;
import models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Mordr on 23.02.2017.
 */
public class UserAuthorizationDAO {
    private final Connection conn;
    private static final Logger logger = Logger.getLogger(UserAuthorizationDAO.class);

    public UserAuthorizationDAO() {
        DatabaseConnector databaseConnector = DatabaseConnector.getInstance();
        this.conn = databaseConnector.getConnection();
    }

    public User findUserByEmailAndPassword(String email, String password) throws DAOException {
        String sql = "select * from user where email = ? and password = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = EntityBoxer.packUser(resultSet, conn);
                logger.trace(user);
                return user;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException();
        }
        return null;
    }
}
