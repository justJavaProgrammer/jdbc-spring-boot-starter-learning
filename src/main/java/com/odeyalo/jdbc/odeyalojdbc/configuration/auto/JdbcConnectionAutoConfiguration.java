package com.odeyalo.jdbc.odeyalojdbc.configuration.auto;

import com.odeyalo.jdbc.odeyalojdbc.configuration.JdbcConnectionConfig;
import com.odeyalo.jdbc.odeyalojdbc.exceptions.ConnectionException;
import com.odeyalo.jdbc.odeyalojdbc.properties.JdbcConnectionProperties;
import com.odeyalo.jdbc.odeyalojdbc.support.DataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ConditionalOnClass(JdbcConnectionConfig.class)
@EnableConfigurationProperties(JdbcConnectionProperties.class)
public class JdbcConnectionAutoConfiguration {

    private final Environment environment;
    private final Logger logger = LoggerFactory.getLogger(JdbcConnectionAutoConfiguration.class);
    private static final String JDBC_CONNECTION_PROPERTIES_DEFAULT_PREFIX = "com.odeyalo.jdbc";

    @Autowired
    public JdbcConnectionAutoConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcConnectionConfig connectionConfig() {
        String username = environment.getProperty(JDBC_CONNECTION_PROPERTIES_DEFAULT_PREFIX + ".username");
        String password = environment.getProperty(JDBC_CONNECTION_PROPERTIES_DEFAULT_PREFIX + ".password");
        String url = environment.getProperty(JDBC_CONNECTION_PROPERTIES_DEFAULT_PREFIX + ".url");
        String driver = environment.getProperty(JDBC_CONNECTION_PROPERTIES_DEFAULT_PREFIX + ".driver");
        this.logger.info("Found jdbc connection username: {}, password: {}, url: {}, driver: {}", username, password, url, driver);
        return new JdbcConnectionConfig(username, password, url, driver);
    }

    @Bean
    @ConditionalOnMissingBean
    public Connection connection(JdbcConnectionConfig connectionConfig) throws ConnectionException {
        try {
             Connection connection = DriverManager.getConnection(connectionConfig.getUrl(), connectionConfig.getUsername(), connectionConfig.getPassword());
             this.logger.info("Successful getting connection to: {}", connectionConfig.getUrl());
             return connection;
        } catch (SQLException ex) {
            throw new ConnectionException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public Driver driver(JdbcConnectionConfig connectionConfig) throws ConnectionException {
        try {
            return DriverManager.getDriver(connectionConfig.getUrl());
        } catch (SQLException ex) {
            throw new ConnectionException("Driver class is wrong");
        }
    }
}
