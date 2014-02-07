package ru.yaal.examples.database.hibernate.inheritance.joined;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class TransportJoinedTest {
    private TransportJoined expTransport;

    @Test
    public void saveTransport() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expTransport = new TransportJoined();
        expTransport.setOwner("owner_joined");

        Factory.save(expTransport);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        TransportJoined actTransport = (TransportJoined) session.get(TransportJoined.class, expTransport.getId());
        assertEquals(expTransport.getId(), actTransport.getId());
        assertEquals(expTransport.getOwner(), actTransport.getOwner());
    }
}
