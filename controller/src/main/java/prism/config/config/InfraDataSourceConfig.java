//package prism.config.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//public class InfraDataSourceConfig {
//
//    // ===== error-read =====
//    @Value("${error.datasource.driver-class-name}")
//    private String errorDriver;
//
//    @Value("${error.datasource.url}")
//    private String errorUrl;
//
//    @Value("${error.datasource.username}")
//    private String errorUser;
//
//    @Value("${error.datasource.password}")
//    private String errorPass;
//
//    // ===== user-read =====
//    @Value("${user.datasource.driver-class-name}")
//    private String userDriver;
//
//    @Value("${user.datasource.url}")
//    private String userUrl;
//
//    @Value("${user.datasource.username}")
//    private String userUser;
//
//    @Value("${user.datasource.password}")
//    private String userPass;
//
//    // ===== subscription-write =====
//    @Value("${subscription.datasource.driver-class-name}")
//    private String subDriver;
//
//    @Value("${subscription.datasource.url}")
//    private String subUrl;
//
//    @Value("${subscription.datasource.username}")
//    private String subUser;
//
//    @Value("${subscription.datasource.password}")
//    private String subPass;
//
//    // 공통 Hikari 빌더 (URL은 그대로, 드라이버 프로퍼티로 keepalive/timeout 전달)
//    private HikariDataSource build(String driver, String url, String user, String pass, String poolName) {
//        HikariDataSource ds = new HikariDataSource();
//        ds.setDriverClassName(driver);
//        ds.setJdbcUrl(url);
//        ds.setUsername(user);
//        ds.setPassword(pass);
//        ds.setPoolName(poolName);
//
//        // --- 공통 방어 옵션 ---
//        ds.setMinimumIdle(1);
//        ds.setMaximumPoolSize(10);
//        ds.setConnectionTimeout(20_000);
//        ds.setValidationTimeout(5_000);
//        ds.setIdleTimeout(60_000);
//        ds.setMaxLifetime(600_000);
//        ds.setKeepaliveTime(30_000);          // Hikari가 주기적으로 ping
//        ds.setConnectionTestQuery("SELECT 1");
//
//        // MariaDB 드라이버 소켓 옵션 (URL에 ? 붙이지 말고 안전하게 프로퍼티로 전달)
//        ds.addDataSourceProperty("tcpKeepAlive", true);
//        // 상황에 맞게 사용 (옵션) — 초 단위
//        ds.addDataSourceProperty("socketTimeout", 20);   // 읽기 타임아웃
//        ds.addDataSourceProperty("connectTimeout", 5);   // 접속 타임아웃
//
//        return ds;
//    }
//
//    // ---------- error-read ----------
//    @Bean(name = "errorReadDataSource")
//    public DataSource errorReadDataSource() {
//        return build(errorDriver, errorUrl, errorUser, errorPass, "error-read-hikari");
//    }
//
//    @Bean(name = "errorReadJdbcTemplate")
//    public JdbcTemplate errorReadJdbcTemplate(DataSource errorReadDataSource) {
//        return new JdbcTemplate(errorReadDataSource);
//    }
//
//    // 읽기 전용이면 Tx 매니저 굳이 필요 X. 필요 시 주석 해제
//    // @Bean(name = "errorReadTxManager")
//    // public PlatformTransactionManager errorReadTxManager(@Qualifier("errorReadDataSource") DataSource ds) {
//    //     return new DataSourceTransactionManager(ds);
//    // }
//
//    // ---------- user-read ----------
//    @Bean(name = "userReadDataSource")
//    public DataSource userReadDataSource() {
//        return build(userDriver, userUrl, userUser, userPass, "user-read-hikari");
//    }
//
//    @Bean(name = "userReadJdbcTemplate")
//    public JdbcTemplate userReadJdbcTemplate(DataSource userReadDataSource) {
//        return new JdbcTemplate(userReadDataSource);
//    }
//
//    // @Bean(name = "userReadTxManager")
//    // public PlatformTransactionManager userReadTxManager(@Qualifier("userReadDataSource") DataSource ds) {
//    //     return new DataSourceTransactionManager(ds);
//    // }
//
//    // ---------- subscription-write ----------
//    @Bean(name = "subscriptionWriteDataSource")
//    public DataSource subscriptionWriteDataSource() {
//        return build(subDriver, subUrl, subUser, subPass, "subscription-write-hikari");
//    }
//
//    @Bean(name = "subscriptionWriteJdbcTemplate")
//    public JdbcTemplate subscriptionWriteJdbcTemplate(DataSource subscriptionWriteDataSource) {
//        return new JdbcTemplate(subscriptionWriteDataSource);
//    }
//
//    // 구독 서비스는 쓰기 트랜잭션이 필요할 수 있으므로 Tx 매니저 제공
//    @Bean(name = "subscriptionTxManager")
//    public PlatformTransactionManager subscriptionTxManager(DataSource subscriptionWriteDataSource) {
//        return new DataSourceTransactionManager(subscriptionWriteDataSource);
//    }
//}
