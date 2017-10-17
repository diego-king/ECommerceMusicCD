package ca.edu.uottawa.csi5380.database.agent.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
public class PooledDbConfig {

    @Bean
    public ComboPooledDataSource dataSource(
            @Value("${spring.datasource.url}") String url,
                                            @Value("${spring.datasource.username}") String username,
                                            @Value("${spring.datasource.password}") String password,
                                            @Value("${spring.datasource.driver-class-name}") String driverClass,
                                            @Value("${database.datasource.min-connections}") int minConnections,
                                            @Value("${database.datasource.max-connections}") int maxConnections
    ) throws PropertyVetoException {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setMinPoolSize(minConnections);
        ds.setMaxPoolSize(maxConnections);
        ds.setMaxIdleTime(600);
        ds.setJdbcUrl(url);
        ds.setUser(username);
        ds.setPassword(password);
        ds.setDriverClass(driverClass);
        return ds;
    }
}
