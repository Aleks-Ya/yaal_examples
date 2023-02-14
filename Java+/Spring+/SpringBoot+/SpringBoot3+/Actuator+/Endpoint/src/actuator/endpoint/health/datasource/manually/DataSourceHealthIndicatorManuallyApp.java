package actuator.endpoint.health.datasource.manually;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static util.ResourceUtil.resourceToPath;

/**
 * Using {@link DataSourceHealthIndicator}.
 * Endpoints:
 * <ul>
 *     <li>http://localhost:8080/actuator/health</li>
 *     <li>http://localhost:8080/actuator/health/dataSource</li>
 * </ul>
 */
@SpringBootApplication
class DataSourceHealthIndicatorManuallyApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location",
                resourceToPath(DataSourceHealthIndicatorManuallyApp.class, "application.yaml"));
        SpringApplication.run(DataSourceHealthIndicatorManuallyApp.class, args);
    }

    @Bean
    DataSourceHealthIndicator dataSourceHealthIndicator(DataSource dataSource) {
        return new DataSourceHealthIndicator(dataSource);
    }

    @Bean
    DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:mem:test1");
    }
}
