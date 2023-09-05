package actuator.endpoint.health.datasource.autoconfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import util.ResourceUtil;

import javax.sql.DataSource;

/**
 * Using {@link DataSourceHealthIndicator}.<br/>
 * Endpoints:
 * <ul>
 *     <li>http://localhost:8080/actuator/health</li>
 *     <li>http://localhost:8080/actuator/health/dataSource</li>
 * </ul>
 */
@SpringBootApplication
class DataSourceHealthIndicatorAutoConfigureApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.location",
                ResourceUtil.resourceToStrPath(DataSourceHealthIndicatorAutoConfigureApp.class, "application.yaml"));
        SpringApplication.run(DataSourceHealthIndicatorAutoConfigureApp.class, args);
    }

    @Bean
    DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:mem:test1");
    }
}
