package prism.config.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "user.datasource", name = "url")
public class UserDbConfig {

    @Bean
    @ConfigurationProperties("user.datasource")
    public DataSourceProperties userDataSourceProperties() { return new DataSourceProperties(); }

    @Bean(name = "userDataSource")
    @ConfigurationProperties("user.datasource.hikari")
    public HikariDataSource userDataSource() {
        HikariDataSource ds = userDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        ds.setReadOnly(true);
        ds.setPoolName("user-read-hikari");
        return ds;
    }

    @Bean(name = "userJdbc")
    public NamedParameterJdbcTemplate userJdbc(@Qualifier("userDataSource") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    @Bean(name = "userTxManager")
    public PlatformTransactionManager userTxManager(
            @Qualifier("userDataSource") javax.sql.DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}
