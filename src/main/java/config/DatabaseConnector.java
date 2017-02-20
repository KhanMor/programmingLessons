package config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Mordr on 16.02.2017.
 */
public class DatabaseConnector {
    private Connection connection;
    private static DatabaseConnector ourInstance = new DatabaseConnector();

    public static DatabaseConnector getInstance() {
        return ourInstance;
    }

    private DatabaseConnector() {
        String url= "jdbc:mysql://localhost:3306/";
        String dbName = "programminglessons";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "khan";
        String password = "khan_megakey";
        try {
            Class.forName(driver).newInstance();
            this.connection = (Connection) DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
