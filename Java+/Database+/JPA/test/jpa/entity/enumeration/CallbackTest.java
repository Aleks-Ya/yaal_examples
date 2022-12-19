package jpa.entity.enumeration;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.getColumnMetaData;
import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

class CallbackTest {
    @Test
    void test() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var id = 1L;
            var person = new Person4Entity(id, "John", Occupation4.DEVELOPER);
            em.persist(person);
            assertThat(em.find(Person4Entity.class, id)).isEqualTo(new Person4Entity(id, "John", Occupation4.DEVELOPER));
            em.getTransaction().commit();
            var columnMap = getColumnMetaData(em.getEntityManagerFactory(), Person4Entity.class.getSimpleName());
            var occupationColumn = columnMap.get("OCCUPATIONVALUE");
            assertThat(occupationColumn.typeName()).isEqualTo("INTEGER");
        }, List.of(Person4Entity.class));
    }

}