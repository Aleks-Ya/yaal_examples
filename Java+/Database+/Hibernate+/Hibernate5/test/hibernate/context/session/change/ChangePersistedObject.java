package hibernate.context.session.change;

import hibernate.context.session.HibernateSessionFactory5;
import org.hibernate.Session;
import org.hibernate.annotations.Immutable;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ChangePersistedObject {

    private static HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);
    private final Session session = factory.getSessionFactory().openSession();

    @Test
    public void saveDetached() {
        assertThat(session.getStatistics().getEntityCount(), equalTo(0));
        House transientObject = new House(null, "Spb");
        Serializable id = session.save(transientObject);
        assertThat(session.getStatistics().getEntityCount(), equalTo(1));

//        transientObject.setAddress("Msk");
        House house2 = new House(transientObject.getId(), "Msk");

        session.save(house2);
        assertThat(session.getStatistics().getEntityCount(), equalTo(1));

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
    @Immutable
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

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getId() {
            return id;
        }
    }
}

