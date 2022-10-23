package hibernate5.context.session.state;

import hibernate5.HibernateSessionFactory5;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static org.assertj.core.api.Assertions.assertThat;

class TransientToPersistedTest {

    private static final HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);
    private final Session session = factory.getSessionFactory().openSession();

    @Test
    void saveTransient() {
        var transientObject = new House(null, "Spb");
        session.beginTransaction();
        var id = session.save(transientObject);
        var persistedObject = session.get(House.class, id);
        assertThat(persistedObject).isSameAs(transientObject);
        session.flush();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void saveOrUpdateTransient() {
        var transientObject = new House(null, "Spb");
        session.beginTransaction();
        session.saveOrUpdate(transientObject);
        int id = transientObject.id;
        var persistedObject = session.get(House.class, id);
        assertThat(persistedObject).isSameAs(transientObject);
        session.flush();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void persistTransient() {
        var transientObject = new House(null, "Spb");
        session.beginTransaction();
        session.persist(transientObject);
        int id = transientObject.id;
        var persistedObject = session.get(House.class, id);
        assertThat(persistedObject).isSameAs(transientObject);
        session.flush();
        session.getTransaction().commit();
        session.close();
    }

    @Entity
    private static class House {
        @Id
        @GeneratedValue
        private Integer id;

        @Column
        private String address;

        House(Integer id, String address) {
            this.id = id;
            this.address = address;
        }
    }
}

