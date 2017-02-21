package config;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Mordr on 16.02.2017.
 */
public class DatabaseConnector {
    private Connection connection;
    private static DatabaseConnector ourInstance = new DatabaseConnector();
    private static final Logger logger = Logger.getLogger(DatabaseConnector.class);

    public static DatabaseConnector getInstance() {
        return ourInstance;
    }

    private DatabaseConnector() {
        String url= "jdbc:mysql://localhost:3306/programminglessons?autoReconnect=true&useSSL=false";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "khan";
        String password = "khan_megakey";
        try {
            Class.forName(driver).newInstance();
            this.connection = DriverManager.getConnection(url,userName,password);
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

    }

    public Connection getConnection() {
        return connection;
    }
}
