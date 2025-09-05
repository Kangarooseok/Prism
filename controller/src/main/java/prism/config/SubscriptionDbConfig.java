package prism.config;

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
@ConditionalOnProperty(prefix = "subscription.datasource", name = "url")
public class SubscriptionDbConfig {

    @Bean
    @ConfigurationProperties("subscription.datasource")
    public DataSourceProperties subscriptionDataSourceProperties() { return new DataSourceProperties(); }

    @Bean(name = "subscriptionDataSource")
    @ConfigurationProperties("subscription.datasource.hikari")
    public HikariDataSource subscriptionDataSource() {
        HikariDataSource ds = subscriptionDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        ds.setPoolName("subscription-write-hikari");
        return ds;
    }

    @Bean(name = "subscriptionJdbc")
    public NamedParameterJdbcTemplate subscriptionJdbc(@Qualifier("subscriptionDataSource") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    @Bean(name = "subscriptionTxManager")
    public PlatformTransactionManager subscriptionTxManager(
            @Qualifier("subscriptionDataSource") javax.sql.DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }


}
