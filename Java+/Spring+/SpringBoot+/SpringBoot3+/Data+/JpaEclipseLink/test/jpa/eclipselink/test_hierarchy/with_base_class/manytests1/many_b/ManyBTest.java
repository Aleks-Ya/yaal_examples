package jpa.eclipselink.test_hierarchy.with_base_class.manytests1.many_b;

import jpa.eclipselink.test_hierarchy.with_base_class.manytests1.BaseEclipseLink1Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
class ManyBTest extends BaseEclipseLink1Test {
    @Autowired
    private VehicleBRepository repo;

    @Test
    void vehicleRepo() {
        var saved = repo.save(new VehicleB(2L, "SuperJet"));
        assertThat(repo.findById(saved.getId())).hasValue(saved);
    }
}
