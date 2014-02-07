package ru.yaal.examples.database.hibernate.inheritance.joined.discriminator;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class TransportJoinedDiscriminatorTest {
    private TransportJoinedDiscriminator expTransport;

    @Test
    public void saveTransport() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expTransport = new TransportJoinedDiscriminator();
        expTransport.setOwner("owner_joined");

        Factory.save(expTransport);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        TransportJoinedDiscriminator actTransport = (TransportJoinedDiscriminator) session.get(TransportJoinedDiscriminator.class, expTransport.getId());
        assertEquals(expTransport.getId(), actTransport.getId());
        assertEquals(expTransport.getOwner(), actTransport.getOwner());
    }
}
