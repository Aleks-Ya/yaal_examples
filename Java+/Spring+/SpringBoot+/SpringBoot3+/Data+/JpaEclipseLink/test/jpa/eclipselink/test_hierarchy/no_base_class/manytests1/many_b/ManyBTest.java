package jpa.eclipselink.test_hierarchy.no_base_class.manytests1.many_b;

import jpa.eclipselink.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext
@EnableAutoConfiguration
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
