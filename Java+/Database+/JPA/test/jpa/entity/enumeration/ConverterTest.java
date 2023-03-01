package jpa.entity.enumeration;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.getColumnMetaData;
import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    @Test
    void test() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var id = 1L;
            var person = new Person5Entity(id, "John", Occupation5.DEVELOPER);
            em.persist(person);
            assertThat(em.find(Person5Entity.class, id)).isEqualTo(new Person5Entity(id, "John", Occupation5.DEVELOPER));
            em.getTransaction().commit();
            var columnMap = getColumnMetaData(em.getEntityManagerFactory(), Person5Entity.class.getSimpleName());
            var occupationColumn = columnMap.get("OCCUPATION");
            assertThat(occupationColumn.typeName()).isEqualTo("CHARACTER VARYING");
        }, List.of(Person5Entity.class), configuration -> {
            configuration.addAttributeConverter(Occupation5.Occupation5Converter.class);
            return configuration;
        });
    }
}