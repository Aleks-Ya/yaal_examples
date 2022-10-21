package hibernate5.naming_strategy;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.DuplicateMappingException;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

import static hibernate5.PhysicalNameHelper.getPhysicalColumnNames;
import static hibernate5.PhysicalNameHelper.getPhysicalTableName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DuplicateMappingExceptionTest {

    @Test
    void duplicateMappingException() {
        assertThatThrownBy(() -> HibernateSessionFactory5.makeFactory(
                Map.of(), new CamelCaseToUnderscoresNamingStrategy(), WrongEntity.class))
                .isInstanceOf(DuplicateMappingException.class)
                .hasMessage("Table [duplicate_mapping_exception_test$wrong_entity] contains physical column name [city_name] referred to by multiple logical column names: [city_name], [cityName]");
    }

    @Test
    void fixedDuplicateMappingException() {
        try (var sessionFactory = HibernateSessionFactory5.makeFactory(
                Map.of(), new CamelCaseToUnderscoresNamingStrategy(), CorrectEntity.class)) {
            assertThat(getPhysicalTableName(sessionFactory, CorrectEntity.class)).contains("duplicate_mapping_exception_test$correct_entity");
            assertThat(getPhysicalColumnNames(sessionFactory, CorrectEntity.class)).contains("city_name_1", "city_name_2");
        }
    }

    @Entity
    static class WrongEntity {
        @Id
        private Long id;

        private String cityName;
        private String city_name;

        @Override
        public String toString() {
            return String.format("Entity[id=%d, cityName=%s, city_name=%s]", id, cityName, city_name);
        }
    }

    @Entity
    static class CorrectEntity {
        @Id
        private Long id;

        @Column(name = "city_name_1")
        private String cityName;
        @Column(name = "city_name_2")
        private String city_name;

        @Override
        public String toString() {
            return String.format("Entity[id=%d, cityName=%s, city_name=%s]", id, cityName, city_name);
        }
    }

}