package jpa.naming_strategy.strategies;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
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

/**
 * {@link CamelCaseToUnderscoresNamingStrategy} is default naming strategy.
 */
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = CamelCaseToUnderscoresNamingStrategyTest.CityEntity.class)
class CamelCaseToUnderscoresNamingStrategyTest {

    @Test
    void camelCaseToUnderscoresNamingStrategy(@Autowired SessionFactory sessionFactory) {
        assertThat(getPhysicalNamingStrategyProperty(sessionFactory)).isEqualTo(CamelCaseToUnderscoresNamingStrategy.class.getName());
        assertThat(getPhysicalTableName(sessionFactory, CityEntity.class)).contains("city_entity");
        assertThat(getPhysicalColumnNames(sessionFactory, CityEntity.class)).contains("city_name", "city_population");
    }

    @Entity
    static class CityEntity {
        @Id
        private Long id;
        private String cityName;
        private Long cityPopulation;
    }
}