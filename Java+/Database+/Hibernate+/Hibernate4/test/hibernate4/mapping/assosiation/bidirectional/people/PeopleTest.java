package hibernate4.mapping.assosiation.bidirectional.people;

import hibernate4.context.session.HibernateSessionFactory436;
import hibernate4.mapping.assosiation.bidirectional.payment.Payment;
import hibernate4.mapping.assosiation.bidirectional.payment.Slip;
import hibernate4.mapping.assosiation.bidirectional.payment.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PeopleTest {
    private static final HibernateSessionFactory436 factory = HibernateSessionFactory436.makeFactory(
            Payment.class, Transaction.class, Slip.class, People.class, Address.class);

    @Test
    void test() throws Exception {
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
            Set<Address> addresses = new HashSet<>();
            addresses.add(moscow);
            addresses.add(spb);

            man.setAddresses(addresses);
            woman.setAddresses(addresses);
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
        List<Address> allAddresses = session.createCriteria(Address.class).addOrder(Order.desc("id")).list();
        List<People> allPeoples = session.createCriteria(People.class).addOrder(Order.desc("id")).list();
        session.close();
    }
}
