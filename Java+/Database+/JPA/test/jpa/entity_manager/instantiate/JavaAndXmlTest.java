package jpa.entity_manager.instantiate;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JavaAndXmlTest {

    private static EntityManager getEntityManager() {
        var props = Map.of(
                "hibernate.connection.driver_class", "org.h2.Driver",
                "hibernate.connection.url", "jdbc:h2:~/test",
                "hibernate.connection.pool_size", "1",
                "hibernate.dialect", "org.hibernate.dialect.H2Dialect",
                "hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory",
                "hibernate.show_sql", "true",
                "hibernate.hbm2ddl.auto", "create",
                "hibernate.connection.autocommit", "false"
        );
        var factory = Persistence.createEntityManagerFactory("persistence-unit-from-xml", props);
        return factory.createEntityManager();
    }

    @Test
    void test() {
        var em = getEntityManager();
        var region = new RegionEntity("Вологодская область");
        em.persist(region);
        assertThat(em.contains(region)).isTrue();
        em.close();
    }
}