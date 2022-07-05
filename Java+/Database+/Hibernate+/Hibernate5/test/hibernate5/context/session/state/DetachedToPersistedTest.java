package hibernate5.context.session.state;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static org.assertj.core.api.Assertions.assertThat;

class DetachedToPersistedTest {
    private static final HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);
    private final Session session = factory.getSessionFactory().openSession();

    @Test
    void saveDetached() {
        var detachedObj = makeDetached();
        var id = session.save(detachedObj);

        var persistedObject = session.get(House.class, id);
        assertThat(persistedObject).isSameAs(detachedObj);

    }

    private House makeDetached() {
        assertThat(session.getStatistics().getEntityCount()).isEqualTo(0);
        var transientObject = new House(null, "Spb");
        var id = session.save(transientObject);
        assertThat(session.getStatistics().getEntityCount()).isEqualTo(1);
        var persistedObject = session.get(House.class, id);
        assertThat(persistedObject).isSameAs(transientObject);
        session.evict(persistedObject);
        assertThat(session.getStatistics().getEntityCount()).isEqualTo(0);
        return persistedObject;
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

