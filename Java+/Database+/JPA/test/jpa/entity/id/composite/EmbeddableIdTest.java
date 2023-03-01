package jpa.entity.id.composite;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jpa.JpaHelper.getColumnMetaData;
import static jpa.JpaHelper.withEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

class EmbeddableIdTest {
    @Test
    void embeddableId() {
        withEntityManager((em) -> {
            em.getTransaction().begin();
            var id = new Person3Id("John", "Smith");
            var person = new Person3Entity(id, 30);
            em.persist(person);
            assertThat(em.find(Person3Entity.class, id)).isEqualTo(new Person3Entity(new Person3Id("John", "Smith"), 30));
            em.getTransaction().commit();
            var columnMap = getColumnMetaData(em.getEntityManagerFactory(), Person3Entity.class.getSimpleName());
            assertThat(columnMap.get("FIRSTNAME").typeName()).isEqualTo("CHARACTER VARYING");
            assertThat(columnMap.get("SECONDNAME").typeName()).isEqualTo("CHARACTER VARYING");
        }, List.of(Person3Entity.class));
    }
}