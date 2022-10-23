package hibernate5.metamodel;

import org.junit.jupiter.api.Test;

import static hibernate5.HibernateSessionFactory5.makeFactory;
import static hibernate5.PhysicalNameHelper.getPhysicalColumnNames;
import static hibernate5.PhysicalNameHelper.getPhysicalTableName;
import static hibernate5.metamodel.CityEntity.COLUMN_NAME;
import static hibernate5.metamodel.CityEntity.TABLE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class GetPhysicalTableNameTest {

    @Test
    void physicalTableName() {
        try (var sessionFactory = makeFactory(CityEntity.class, PersonEntity.class)) {
            assertThat(getPhysicalTableName(sessionFactory.getSessionFactory(), CityEntity.class)).isEqualTo(TABLE_NAME);
            assertThat(getPhysicalTableName(sessionFactory.getSessionFactory(), PersonEntity.class)).isEqualTo("PersonEntity");
        }
    }

    @Test
    void physicalColumnNames() {
        try (var sessionFactory = makeFactory(CityEntity.class, PersonEntity.class)) {
            assertThat(getPhysicalColumnNames(sessionFactory.getSessionFactory(), CityEntity.class))
                    .containsExactlyInAnyOrder(COLUMN_NAME, "cityPopulation");
            assertThat(getPhysicalColumnNames(sessionFactory.getSessionFactory(), PersonEntity.class))
                    .containsExactlyInAnyOrder("personName", "personAge");
        }
    }

}