package hibernate5.context.session.state;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertNull;

class PersistedToDetached {
    private static final HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);
    private final Session session = factory.getSessionFactory().openSession();

    @Test
    void evictPersisted() {
        var transientObject = new House(null, "Spb");

        session.beginTransaction();

        var id = session.save(transientObject);
        assertThat(session.getStatistics().getEntityCount(), equalTo(1));

        var persistedObject = session.get(House.class, id);
        assertThat(persistedObject, sameInstance(transientObject));

        session.evict(persistedObject);
        assertThat(session.getStatistics().getEntityCount(), equalTo(0));

        assertNull(session.get(House.class, id));
        assertThat(persistedObject, sameInstance(transientObject));

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

