package jpa.naming_strategy.strategies;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.Entity;
import javax.persistence.Id;

import static hibernate5.PhysicalNameHelper.getPhysicalColumnNames;
import static hibernate5.PhysicalNameHelper.getPhysicalNamingStrategyProperty;
import static hibernate5.PhysicalNameHelper.getPhysicalTableName;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl")
@EnableAutoConfiguration
@ContextConfiguration(classes = PhysicalNamingStrategyStandardImplTest.CityEntity.class)
class PhysicalNamingStrategyStandardImplTest {

    @Test
    void springPhysicalNamingStrategy(@Autowired SessionFactory sessionFactory) {
        assertThat(getPhysicalNamingStrategyProperty(sessionFactory)).isEqualTo(PhysicalNamingStrategyStandardImpl.class.getName());
        assertThat(getPhysicalTableName(sessionFactory, CityEntity.class)).contains("PhysicalNamingStrategyStandardImplTest$CityEntity");
        assertThat(getPhysicalColumnNames(sessionFactory, CityEntity.class)).contains("cityName", "cityPopulation");
    }

    @Entity
    static class CityEntity {
        @Id
        private Long id;
        private String cityName;
        private Long cityPopulation;
    }
}