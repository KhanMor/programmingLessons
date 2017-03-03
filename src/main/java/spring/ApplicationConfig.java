package spring;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Mordr on 01.03.2017.
 * Конфигурация
 */
@Configuration
@ComponentScan({"servicesimpl", "models.daoimpl"})
public class ApplicationConfig {
    private static final Logger logger = Logger.getLogger(ApplicationConfig.class);
    static {
        DOMConfigurator.configure("../resources/log4j.xml");
    }

    @Bean
    public DataSource getDataSource() {
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
        DataSource dataSource= null;
        try {
            dataSource = dataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            logger.error(e);
        }
        logger.trace(dataSource);
        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/programminglessons?autoReconnect=true&useSSL=false");
        properties.put("hibernate.connection.username", "khan");
        properties.put("hibernate.connection.password", "khan_megakey");
        properties.put("hibernate.c3p0.min_size", "5");
        properties.put("hibernate.c3p0.max_size", "20");
        properties.put("hibernate.c3p0.timeout", "1800");
        properties.put("hibernate.c3p0.max_statements", "50");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");

        return properties;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    //@Autowired
    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean(/*DataSource dataSource*/) {
        LocalContainerEntityManagerFactoryBean container = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        //container.setDataSource(dataSource);
        container.setJpaProperties(getHibernateProperties());
        container.setPackagesToScan("models.pojo");
        container.setJpaVendorAdapter(jpaVendorAdapter);
        return container;
    }
    @Autowired
    @Bean(name = "transactionManager")
    public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
