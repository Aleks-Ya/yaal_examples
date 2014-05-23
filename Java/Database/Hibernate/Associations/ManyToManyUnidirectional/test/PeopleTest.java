import org.hibernate.Session;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PeopleTest {

    @Test
    public void test() throws Exception {
        saveEntities();
        readEntities();
    }

    private void saveEntities() throws Exception {
        Session session = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
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
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

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
