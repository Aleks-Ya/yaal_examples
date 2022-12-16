package eclipselink;

import org.eclipse.persistence.config.TargetServer;
import org.junit.jupiter.api.Test;

import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_URL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TRANSACTION_TYPE;

class GetEntityManagerFactoryTest {
    @Test
    void fromStandardPersistenceXml() {
        var emf = Persistence.createEntityManagerFactory("pu1");
        var em = emf.createEntityManager();
        var id = 1L;
        var expPerson = new Person(id, "John");
        em.getTransaction().begin();
        em.merge(expPerson);
        em.getTransaction().commit();
        var actPerson = em.find(Person.class, id);
        assertThat(actPerson).isEqualTo(expPerson);
    }

    @Test
    void fromCustomPersistenceXml() {
        var properties = new Properties();
        properties.put(ECLIPSELINK_PERSISTENCE_XML, "eclipselink/persistence_custom.xml");
        var emf = Persistence.createEntityManagerFactory("custom_persistence_unit", properties);
        var em = emf.createEntityManager();
        var id = 2L;
        var expPerson = new Person(id, "John");
        em.getTransaction().begin();
        em.persist(expPerson);
        em.getTransaction().commit();
        var actPerson = em.find(Person.class, id);
        assertThat(actPerson).isEqualTo(expPerson);
    }

    /**
     * Override persistence.xml properties with Properties object.
     */
    @Test
    void fromPersistenceXmlAndProperties() {
        var properties = new Properties();
        properties.put(ECLIPSELINK_PERSISTENCE_XML, "eclipselink/persistence_short.xml");
        properties.put(TRANSACTION_TYPE, PersistenceUnitTransactionType.RESOURCE_LOCAL.name());
        properties.put(JDBC_DRIVER, org.h2.Driver.class.getName());
        properties.put(JDBC_URL, "jdbc:h2:mem:");
        properties.put(LOGGING_LEVEL, "FINE");
        properties.put(TARGET_SERVER, TargetServer.None);
        properties.put(DDL_GENERATION, "create-tables");
        var emf = Persistence.createEntityManagerFactory("short_persistence_unit", properties);

        var em = emf.createEntityManager();
        var id = 1L;
        var expPerson = new Person(id, "John");
        em.getTransaction().begin();
        em.persist(expPerson);
        em.getTransaction().commit();
        var actPerson = em.find(Person.class, id);
        assertThat(actPerson).isEqualTo(expPerson);
    }
}
