package ru.yaal.examples.database.hibernate.inheritance.tableperclass;

import org.hibernate.Session;
import org.junit.Test;
import ru.yaal.examples.database.hibernate.inheritance.Factory;

import static org.junit.Assert.assertEquals;

public class TransportTablePerClassTest {
    private TransportTablePerClass expTransport;

    @Test
    public void saveTransport() throws Exception {
        save();
        load();
    }

    private void save() throws Exception {
        expTransport = new TransportTablePerClass();
        expTransport.setOwner("me");

        Factory.save(expTransport);
    }

    private void load() throws Exception {
        Session session = Factory.getSessionFactory().openSession();
        TransportTablePerClass actTransport = (TransportTablePerClass) session.get(TransportTablePerClass.class, expTransport.getId());
        assertEquals(expTransport.getId(), actTransport.getId());
        assertEquals(expTransport.getOwner(), actTransport.getOwner());
    }
}
