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
import static org.hamcrest.MatcherAssert.assertThat;

public class ChangePersistedObject {

    private static HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);
    private final Session session = factory.getSessionFactory().openSession();

    @Test
    public void changePersisted() {
        assertThat(session.getStatistics().getEntityCount(), equalTo(0));

        House transientObject = new House(null, "Spb");

        Serializable oldId = session.save(transientObject);
        assertThat(session.getStatistics().getEntityCount(), equalTo(1));

        House persistedObject = session.get(House.class, oldId);

        String newAddress = "Msk";
        persistedObject.setAddress(newAddress);

        Serializable newId = session.save(persistedObject);
        assertThat(newId, equalTo(oldId));
        assertThat(persistedObject.getId(), equalTo(oldId));
        assertThat(session.getStatistics().getEntityCount(), equalTo(1));

        House newPersistedObject = session.get(House.class, oldId);
        assertThat(newPersistedObject.getAddress(), equalTo(newAddress));
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

        String getAddress() {
            return address;
        }

        void setAddress(String address) {
            this.address = address;
        }

        Integer getId() {
            return id;
        }
    }
}

