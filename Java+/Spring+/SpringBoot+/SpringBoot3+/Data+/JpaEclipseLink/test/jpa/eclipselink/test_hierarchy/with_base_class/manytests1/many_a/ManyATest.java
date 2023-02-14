package jpa.eclipselink.test_hierarchy.with_base_class.manytests1.many_a;

import jpa.eclipselink.test_hierarchy.with_base_class.manytests1.BaseEclipseLink1Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
class ManyATest extends BaseEclipseLink1Test {
    @Autowired
    private VehicleARepository repo;

    @Test
    void vehicleRepo() {
        var saved = repo.save(new VehicleA(2L, "SuperJet"));
        assertThat(repo.findById(saved.getId())).hasValue(saved);
    }
}
