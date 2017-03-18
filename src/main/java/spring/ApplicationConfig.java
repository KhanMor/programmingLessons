package spring;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import spring.security.SecurityApplicationConfig;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Mordr on 01.03.2017.
 * Конфигурация
 */
@Configuration
@ComponentScan({"servicesimpl", "models.daoimpl", "controllers.mvc"})
@EnableWebMvc
@Import(SecurityApplicationConfig.class)
public class ApplicationConfig extends WebMvcConfigurerAdapter {
    private static final Logger logger = Logger.getLogger(ApplicationConfig.class);
    static {
        DOMConfigurator.configure("log4j.xml");
    }

    @Bean
    @Deprecated
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
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean(/*DataSource dataSource*/) {
        LocalContainerEntityManagerFactoryBean container = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        container.setJpaProperties(getHibernateProperties());
        container.setPackagesToScan("models.entity");
        container.setJpaVendorAdapter(jpaVendorAdapter);
        return container;
    }
    @Bean
    public UrlBasedViewResolver getViewResolver() {
        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
        urlBasedViewResolver.setPrefix("/");
        urlBasedViewResolver.setSuffix(".jsp");
        urlBasedViewResolver.setViewClass(JstlView.class);
        return urlBasedViewResolver;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
    @Bean
    public CustomExceptionResolver createSimpleMappingExceptionResolver() {
        CustomExceptionResolver exceptionResolver = new CustomExceptionResolver();
        Properties exceptionMappings = new Properties();
        exceptionMappings.put("AccessDeniedException", "error.accessDenied");
        exceptionResolver.setDefaultErrorView("error");
        exceptionResolver.setExceptionMappings(exceptionMappings);
        return exceptionResolver;
    }
}
