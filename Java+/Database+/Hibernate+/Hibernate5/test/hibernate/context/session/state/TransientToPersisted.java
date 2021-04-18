package hibernate.context.session.state;

import hibernate.context.session.HibernateSessionFactory5;
import org.hamcrest.Matchers;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

import static org.hamcrest.MatcherAssert.assertThat;

public class TransientToPersisted {

    private static HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);
    private final Session session = factory.getSessionFactory().openSession();

    @Test
    public void saveTransient() {
        House transientObject = new House(null, "Spb");
        Serializable id = session.save(transientObject);
        House persistedObject = session.get(House.class, id);
        assertThat(persistedObject, Matchers.sameInstance(transientObject));
        session.flush();
        session.close();
    }

    @Test
    public void saveOrUpdateTransient() {
        House transientObject = new House(null, "Spb");
        session.saveOrUpdate(transientObject);
        int id = transientObject.id;
        House persistedObject = session.get(House.class, id);
        assertThat(persistedObject, Matchers.sameInstance(transientObject));
        session.flush();
        session.close();
    }

    @Test
    public void persistTransient() {
        House transientObject = new House(null, "Spb");
        session.persist(transientObject);
        int id = transientObject.id;
        House persistedObject = session.get(House.class, id);
        assertThat(persistedObject, Matchers.sameInstance(transientObject));
        session.flush();
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

