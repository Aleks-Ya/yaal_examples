package jpa.entity_manager.instantiate;

import jpa.H2Server;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

class H2ServerTest {

    private static EntityManager getEntityManager(String url) {
        var configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.pool_size", "1")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCachingRegionFactory")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.connection.autocommit", "false")
                .addAnnotatedClass(RegionEntity.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        var emFactory = configuration.buildSessionFactory(serviceRegistry);
        return emFactory.createEntityManager();
    }

    @Test
    void test() {
        try (var h2Server = H2Server.newServer()) {
            var url = h2Server.newDatabaseUrl();
            var em = getEntityManager(url);
            var region = new RegionEntity("Вологодская область");
            em.persist(region);
            assertThat(em.contains(region)).isTrue();
        }
    }
}