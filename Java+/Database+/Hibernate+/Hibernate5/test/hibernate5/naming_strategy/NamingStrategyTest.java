package hibernate5.naming_strategy;

import hibernate5.HibernateSessionFactory5;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.junit.jupiter.api.Test;

import static hibernate5.PhysicalNameHelper.getPhysicalColumnNames;
import static hibernate5.PhysicalNameHelper.getPhysicalNamingStrategyProperty;
import static hibernate5.PhysicalNameHelper.getPhysicalTableName;
import static hibernate5.naming_strategy.CityEntity.COLUMN_NAME;
import static hibernate5.naming_strategy.CityEntity.TABLE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class NamingStrategyTest {

    @Test
    void defaultPhysicalNamingStrategyStandardImpl() {
        try (var sessionFactory = HibernateSessionFactory5.makeFactory(CityEntity.class, PersonEntity.class)) {
            assertThat(sessionFactory.getImplicitNamingStrategy()).isInstanceOf(ImplicitNamingStrategyJpaCompliantImpl.class);
            assertThat(sessionFactory.getPhysicalNamingStrategy()).isInstanceOf(PhysicalNamingStrategyStandardImpl.class);
            assertThat(getPhysicalNamingStrategyProperty(sessionFactory)).isNull();

            assertThat(getPhysicalTableName(sessionFactory, CityEntity.class)).contains(TABLE_NAME);
            assertThat(getPhysicalColumnNames(sessionFactory, CityEntity.class)).contains("cityPopulation", COLUMN_NAME);

            assertThat(getPhysicalTableName(sessionFactory, PersonEntity.class)).contains("PersonEntity");
            assertThat(getPhysicalColumnNames(sessionFactory, PersonEntity.class)).contains("personName", "personAge");
        }
    }

    @Test
    void camelCaseToUnderscoresNamingStrategy() {
        try (var sessionFactory = HibernateSessionFactory5.makeFactory((configuration) -> {
            configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
            configuration.addAnnotatedClass(CityEntity.class);
            configuration.addAnnotatedClass(PersonEntity.class);
        })) {
            assertThat(sessionFactory.getImplicitNamingStrategy()).isInstanceOf(ImplicitNamingStrategyJpaCompliantImpl.class);
            assertThat(sessionFactory.getPhysicalNamingStrategy()).isInstanceOf(CamelCaseToUnderscoresNamingStrategy.class);
            assertThat(getPhysicalNamingStrategyProperty(sessionFactory)).isNull();

            assertThat(getPhysicalTableName(sessionFactory, CityEntity.class)).contains(TABLE_NAME);
            assertThat(getPhysicalColumnNames(sessionFactory, CityEntity.class)).contains("city_population", COLUMN_NAME);

            assertThat(getPhysicalTableName(sessionFactory, PersonEntity.class)).contains("person_entity");
            assertThat(getPhysicalColumnNames(sessionFactory, PersonEntity.class)).contains("person_name", "person_age");
        }
    }

}