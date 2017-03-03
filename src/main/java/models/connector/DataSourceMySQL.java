package models.connector;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Mordr on 27.02.2017.
 */
@Deprecated
public class DataSourceMySQL {
    private DataSource dataSource;
    private static DataSourceMySQL dataSourceMySQL = new DataSourceMySQL();
    private static final Logger logger = Logger.getLogger(DataSourceMySQL.class);

    public static DataSourceMySQL getInstance() {
        return dataSourceMySQL;
    }

    private DataSourceMySQL() {
        try {
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            Properties properties = new Properties();
            properties.put("name", "DataSourceMySQL");
            properties.put("driverClassName", "com.mysql.jdbc.Driver");
            properties.put("username", "khan");
            properties.put("password", "khan_megakey");
            properties.put("url", "jdbc:mysql://localhost:3306/programminglessons?autoReconnect=true&useSSL=false");
            properties.put("maxTotal", "10");
            properties.put("maxIdle", "100");
            properties.put("maxWaitMillis", "10000");
            properties.put("removeAbandonedTimeout", "300");
            this.dataSource= dataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
