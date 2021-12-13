package hibernate5.context.session.state;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

public class DetachedToPersisted {

    private static HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);
    private final Session session = factory.getSessionFactory().openSession();

    @Test
    public void saveDetached() {
        House detachedObj = makeDetached();
        Serializable id = session.save(detachedObj);

        House persistedObject = session.get(House.class, id);
        assertThat(persistedObject, sameInstance(detachedObj));

    }

    private House makeDetached() {
        assertThat(session.getStatistics().getEntityCount(), equalTo(0));
        House transientObject = new House(null, "Spb");
        Serializable id = session.save(transientObject);
        assertThat(session.getStatistics().getEntityCount(), equalTo(1));
        House persistedObject = session.get(House.class, id);
        assertThat(persistedObject, sameInstance(transientObject));
        session.evict(persistedObject);
        assertThat(session.getStatistics().getEntityCount(), equalTo(0));
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

