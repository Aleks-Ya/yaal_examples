package jpa.naming_strategy.strategies;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.Entity;
import javax.persistence.Id;

import static hibernate5.PhysicalNameHelper.getPhysicalColumnNames;
import static hibernate5.PhysicalNameHelper.getPhysicalNamingStrategyProperty;
import static hibernate5.PhysicalNameHelper.getPhysicalTableName;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.naming.physical-strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy")
@EnableAutoConfiguration
@ContextConfiguration(classes = SpringPhysicalNamingStrategyTest.CityEntity.class)
class SpringPhysicalNamingStrategyTest {

    @Test
    void springPhysicalNamingStrategy(@Autowired SessionFactory sessionFactory) {
        assertThat(getPhysicalNamingStrategyProperty(sessionFactory)).isEqualTo(SpringPhysicalNamingStrategy.class.getName());
        assertThat(getPhysicalTableName(sessionFactory, CityEntity.class)).contains("spring_physical_naming_strategy_test$city_entity");
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