package edu.java.scrapper.configuration;

import javax.sql.DataSource;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource", ignoreUnknownFields = false)
@Setter
public class JdbcConfig {

    private String driver;
    private String url;
    private String schema;
    private String username;
    private String password;

    @Bean(name = "jdbcSource")
    public DataSource jdbcSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setSchema(schema);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
