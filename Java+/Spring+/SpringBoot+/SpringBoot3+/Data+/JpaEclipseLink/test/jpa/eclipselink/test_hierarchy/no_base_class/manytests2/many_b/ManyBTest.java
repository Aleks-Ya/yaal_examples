package jpa.eclipselink.test_hierarchy.no_base_class.manytests2.many_b;

import jpa.eclipselink.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext
@EntityScan
@EnableJpaRepositories
@ContextConfiguration(classes = JpaConfig.class)
class ManyBTest {
    @Autowired
    private VehicleBRepository repo;

    @Test
    void vehicleRepo() {
        var saved = repo.save(new VehicleB(2L, "SuperJet"));
        assertThat(repo.findById(saved.getId())).hasValue(saved);
    }
}
