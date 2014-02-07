package ru.yaal.examples.database.hibernate.inheritance.single.discriminator;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;
import ru.yaal.examples.database.hibernate.inheritance.single.TransportSingle;

import static org.junit.Assert.assertEquals;

public class TransportSingleDiscriminatorTest {
    private TransportSingleDiscriminator expTransport;

    @Test
    public void saveTransport() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expTransport = new TransportSingleDiscriminator();
        expTransport.setOwner("me");

        Factory.save(expTransport);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        TransportSingleDiscriminator actTransport = (TransportSingleDiscriminator)
                session.get(TransportSingleDiscriminator.class, expTransport.getId());
        assertEquals(expTransport.getId(), actTransport.getId());
        assertEquals(expTransport.getOwner(), actTransport.getOwner());
    }
}
