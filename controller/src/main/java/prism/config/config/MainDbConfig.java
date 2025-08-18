package prism.config.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class MainDbConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSource") // JPA가 기본으로 쓰는 이름
    @Primary
    public DataSource mainDataSource() {
        HikariDataSource ds = mainDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        ds.setPoolName("main-hikari");
        return ds;
    }
}
