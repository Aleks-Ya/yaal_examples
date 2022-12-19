package jpa.entity.enumeration;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.getColumnMetaData;
import static jpa.JpaHelper.withEntityManager;
import static jpa.entity.enumeration.Occupation.DEVELOPER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use an enum without any annotations.
 */
class DefaultTest {
    @Test
    void enumeration() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var id = 1L;
            var person = new PersonEntity(id, "John", DEVELOPER);
            em.persist(person);
            assertThat(em.find(PersonEntity.class, id)).isEqualTo(new PersonEntity(id, "John", DEVELOPER));
            em.getTransaction().commit();
            var columnMap = getColumnMetaData(em.getEntityManagerFactory(), PersonEntity.class.getSimpleName());
            var occupationColumn = columnMap.get("OCCUPATION");
            assertThat(occupationColumn.typeName()).isEqualTo("INTEGER");
        }, List.of(PersonEntity.class));
    }
}