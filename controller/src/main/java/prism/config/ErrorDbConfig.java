package prism.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "error.datasource", name = "url") // URL 없으면 전체 ReadDbConfig 건너뜀
public class ErrorDbConfig {

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
    @ConditionalOnBean(name = "errorDataSource") // DataSource 없으면 템플릿도 생성 안 함
    public NamedParameterJdbcTemplate errorJdbc(@Qualifier("errorDataSource") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }
}
