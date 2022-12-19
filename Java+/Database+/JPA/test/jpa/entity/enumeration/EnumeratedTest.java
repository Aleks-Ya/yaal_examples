package jpa.entity.enumeration;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.getColumnMetaData;
import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

class EnumeratedTest {
    @Test
    void ordinal() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var id = 1L;
            var person = new Person2Entity(id, "John", Occupation2.DEVELOPER);
            em.persist(person);
            assertThat(em.find(Person2Entity.class, id)).isEqualTo(new Person2Entity(id, "John", Occupation2.DEVELOPER));
            em.getTransaction().commit();
            var columnMap = getColumnMetaData(em.getEntityManagerFactory(), Person2Entity.class.getSimpleName());
            var occupationColumn = columnMap.get("OCCUPATION");
            assertThat(occupationColumn.typeName()).isEqualTo("INTEGER");
        }, List.of(Person2Entity.class));
    }

    @Test
    void string() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var id = 1L;
            var person = new Person3Entity(id, "John", Occupation3.DEVELOPER);
            em.persist(person);
            assertThat(em.find(Person3Entity.class, id)).isEqualTo(new Person3Entity(id, "John", Occupation3.DEVELOPER));
            em.getTransaction().commit();
            var columnMap = getColumnMetaData(em.getEntityManagerFactory(), Person3Entity.class.getSimpleName());
            var occupationColumn = columnMap.get("OCCUPATION");
            assertThat(occupationColumn.typeName()).isEqualTo("VARCHAR");
        }, List.of(Person3Entity.class));
    }
}