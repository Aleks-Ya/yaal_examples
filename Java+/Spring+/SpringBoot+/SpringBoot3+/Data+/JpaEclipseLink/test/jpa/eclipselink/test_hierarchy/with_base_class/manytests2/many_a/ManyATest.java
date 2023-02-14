package jpa.eclipselink.test_hierarchy.with_base_class.manytests2.many_a;

import jpa.eclipselink.test_hierarchy.with_base_class.manytests2.BaseEclipseLink2Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.assertj.core.api.Assertions.assertThat;

@EntityScan
@EnableJpaRepositories
class ManyATest extends BaseEclipseLink2Test {
    @Autowired
    private VehicleARepository repo;

    @Test
    void vehicleRepo() {
        var saved = repo.save(new VehicleA(2L, "SuperJet"));
        assertThat(repo.findById(saved.getId())).hasValue(saved);
    }
}
