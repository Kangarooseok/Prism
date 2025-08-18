package prism.config.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ReadDbConfig {

    @Bean
    @ConfigurationProperties("error.datasource")
    public DataSourceProperties errorDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "errorDataSource")
    @ConfigurationProperties("error.datasource.hikari")
    public HikariDataSource errorDataSource() {
        HikariDataSource ds = errorDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        ds.setReadOnly(true);
        ds.setPoolName("error-read-hikari");
        return ds;
    }

    @Bean(name = "errorJdbc")
    public NamedParameterJdbcTemplate errorJdbc(@Qualifier("errorDataSource") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }
}
