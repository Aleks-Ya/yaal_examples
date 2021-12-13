package hibernate5.context.session.change;

import hibernate5.context.session.HibernateSessionFactory5;
import org.hibernate.Session;
import org.hibernate.annotations.Immutable;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ChangePersistedObject {
    private static final HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);
    private final Session session = factory.getSessionFactory().openSession();

    @Test
    void changePersisted() {
        assertThat(session.getStatistics().getEntityCount(), equalTo(0));

        var transientObject = new House(null, "Spb");

        var oldId = session.save(transientObject);
        assertThat(session.getStatistics().getEntityCount(), equalTo(1));

        var persistedObject = session.get(House.class, oldId);

        var newAddress = "Msk";
        persistedObject.setAddress(newAddress);

        var newId = session.save(persistedObject);
        assertThat(newId, equalTo(oldId));
        assertThat(persistedObject.getId(), equalTo(oldId));
        assertThat(session.getStatistics().getEntityCount(), equalTo(1));

        var newPersistedObject = session.get(House.class, oldId);
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

