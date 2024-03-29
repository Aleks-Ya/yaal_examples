package server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;


public class EnvironmentRepositoryTest extends BaseTest {
    @Autowired
    private EnvironmentRepository repo;

    @Test
    void applicationProfile() {
        Environment environment = repo.findOne("app1", "profileA", null);
        List<PropertySource> propertySources = environment.getPropertySources();
        assertThat(propertySources).hasSize(3));
    }
}
