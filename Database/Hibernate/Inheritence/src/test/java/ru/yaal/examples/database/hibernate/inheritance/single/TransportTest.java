package ru.yaal.examples.database.hibernate.inheritance.single;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class TransportTest {
    private Transport expTransport;

    @Test
    public void saveTransport() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expTransport = new Transport();
        expTransport.setOwner("me");

        Session session = Factory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(expTransport);
        session.getTransaction().commit();
        session.flush();
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        Transport actTransport = (Transport) session.get(Transport.class, expTransport.getId());
        assertEquals(expTransport.getOwner(), actTransport.getOwner());
    }
}
