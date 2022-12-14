package jpa.metamodel;

import org.junit.jupiter.api.Test;

import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import java.util.List;

import static java.util.function.Function.identity;
import static javax.persistence.metamodel.Type.PersistenceType.EMBEDDABLE;
import static javax.persistence.metamodel.Type.PersistenceType.ENTITY;
import static javax.persistence.metamodel.Type.PersistenceType.MAPPED_SUPERCLASS;
import static jpa.JpaHelper.withEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;

class MetamodelTest {
    @Test
    void getEntities() {
        withEntityManagerFactory((emFactory) -> {
            var metamodel = emFactory.getMetamodel();

            var entities = metamodel.getEntities();
            assertThat(entities).allSatisfy(element -> assertThat(element.getPersistenceType()).isEqualTo(ENTITY));
            assertThat(entities).map(EntityType::getJavaType)
                    .containsExactlyInAnyOrder((Class) CityEntity.class, (Class) RegionEntity.class);

            var embeddables = metamodel.getEmbeddables();
            assertThat(embeddables).allSatisfy(element -> assertThat(element.getPersistenceType()).isEqualTo(EMBEDDABLE));
            assertThat(embeddables).map(EmbeddableType::getJavaType).containsExactlyInAnyOrder((Class) Mayor.class);

            var managedTypes = metamodel.getManagedTypes();
            assertThat(managedTypes).map(ManagedType::getJavaType)
                    .containsExactlyInAnyOrder((Class) CityEntity.class, (Class) RegionEntity.class, (Class) Mayor.class,
                            (Class) BaseEntity.class);

            var baseManagedType = metamodel.managedType(BaseEntity.class);
            assertThat(baseManagedType.getJavaType()).isEqualTo(BaseEntity.class);
            assertThat(baseManagedType.getPersistenceType()).isEqualTo(MAPPED_SUPERCLASS);

            var entityType = metamodel.entity(CityEntity.class);
            assertThat(entityType.getJavaType()).isEqualTo(CityEntity.class);
            assertThat(entityType.getPersistenceType()).isEqualTo(ENTITY);

            var embeddableType = metamodel.embeddable(Mayor.class);
            assertThat(embeddableType.getJavaType()).isEqualTo(Mayor.class);
            assertThat(embeddableType.getPersistenceType()).isEqualTo(EMBEDDABLE);

            var managedType = metamodel.managedType(RegionEntity.class);
            assertThat(managedType.getJavaType()).isEqualTo(RegionEntity.class);
            assertThat(managedType.getPersistenceType()).isEqualTo(ENTITY);
        }, List.of(CityEntity.class, RegionEntity.class), identity());
    }
}