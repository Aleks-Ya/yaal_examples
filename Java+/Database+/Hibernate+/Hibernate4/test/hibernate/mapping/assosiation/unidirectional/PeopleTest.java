package hibernate.mapping.assosiation.unidirectional;

import hibernate.context.session.HibernateSessionFactory436;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeopleTest {
    private static final HibernateSessionFactory436 factory = HibernateSessionFactory436.makeFactory(
            People.class, Address.class);

    @Test
    public void test() throws Exception {
        saveEntities();
        readEntities();
    }

    private void saveEntities() {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();

            People man = new People();
            People woman = new People();
            Set<People> peoples = new HashSet<>();
            peoples.add(man);
            peoples.add(woman);

            Address spb = new Address();
            Address moscow = new Address();

            moscow.setPeoples(peoples);
            spb.setPeoples(peoples);

            session.save(man);
            session.save(woman);
            session.save(moscow);
            session.save(spb);
            session.getTransaction().commit();
            session.flush();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void readEntities() throws Exception {
        Session session = factory.openSession();

        List<People> allPeoples = session.createCriteria(People.class).list();
        final int expPeopleSize = 2;
        assertEquals(expPeopleSize, allPeoples.size());

        List<Address> allAddresses = session.createCriteria(Address.class).list();
        for (Address address : allAddresses) {
            assertEquals(expPeopleSize, address.getPeoples().size());
        }

        session.close();
    }
}
