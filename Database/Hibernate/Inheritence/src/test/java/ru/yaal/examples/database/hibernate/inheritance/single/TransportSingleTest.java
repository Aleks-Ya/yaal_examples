package ru.yaal.examples.database.hibernate.inheritance.single;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class TransportSingleTest {
    private TransportSingle expTransport;

    @Test
    public void saveTransport() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expTransport = new TransportSingle();
        expTransport.setOwner("me");

        Factory.save(expTransport);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        TransportSingle actTransport = (TransportSingle) session.get(TransportSingle.class, expTransport.getId());
        assertEquals(expTransport.getId(), actTransport.getId());
        assertEquals(expTransport.getOwner(), actTransport.getOwner());
    }
}
