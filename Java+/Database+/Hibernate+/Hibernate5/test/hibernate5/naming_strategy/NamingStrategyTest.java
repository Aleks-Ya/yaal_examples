package hibernate5.naming_strategy;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static hibernate5.PhysicalNameHelper.getPhysicalColumnNames;
import static hibernate5.PhysicalNameHelper.getPhysicalTableName;
import static hibernate5.naming_strategy.CityEntity.COLUMN_NAME;
import static hibernate5.naming_strategy.CityEntity.TABLE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class NamingStrategyTest {

    @Test
    void defaultPhysicalNamingStrategyStandardImpl() {
        try (var sessionFactory = HibernateSessionFactory5.makeFactory(CityEntity.class, PersonEntity.class)) {
            assertThat(getPhysicalTableName(sessionFactory, CityEntity.class)).contains(TABLE_NAME);
            assertThat(getPhysicalColumnNames(sessionFactory, CityEntity.class)).contains("cityPopulation", COLUMN_NAME);
            assertThat(getPhysicalTableName(sessionFactory, PersonEntity.class)).contains("PersonEntity");
            assertThat(getPhysicalColumnNames(sessionFactory, PersonEntity.class)).contains("personName", "personAge");
        }
    }

    @Test
    void camelCaseToUnderscoresNamingStrategy() {
        try (var sessionFactory = HibernateSessionFactory5.makeFactory(
                Map.of(), new CamelCaseToUnderscoresNamingStrategy(), CityEntity.class, PersonEntity.class)) {
            assertThat(getPhysicalTableName(sessionFactory, CityEntity.class)).contains(TABLE_NAME);
            assertThat(getPhysicalColumnNames(sessionFactory, CityEntity.class)).contains("city_population", COLUMN_NAME);
            assertThat(getPhysicalTableName(sessionFactory, PersonEntity.class)).contains("person_entity");
            assertThat(getPhysicalColumnNames(sessionFactory, PersonEntity.class)).contains("person_name", "person_age");
        }
    }

}