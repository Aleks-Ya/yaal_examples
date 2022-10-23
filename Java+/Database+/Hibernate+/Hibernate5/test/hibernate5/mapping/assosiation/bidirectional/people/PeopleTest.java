package hibernate5.mapping.assosiation.bidirectional.people;

import hibernate5.HibernateSessionFactory5;
import hibernate5.mapping.assosiation.bidirectional.payment.Payment;
import hibernate5.mapping.assosiation.bidirectional.payment.Slip;
import hibernate5.mapping.assosiation.bidirectional.payment.Transaction;
import org.hibernate.criterion.Order;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class PeopleTest {
    private static final HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(
            Payment.class, Transaction.class, Slip.class, People.class, Address.class);

    @Test
    void test() {
        saveEntities();
        readEntities();
    }

    private void saveEntities() {
        try (var session = factory.openSession()) {
            session.beginTransaction();

            var man = new People();
            var woman = new People();
            Set<People> peoples = new HashSet<>();
            peoples.add(man);
            peoples.add(woman);

            var spb = new Address();
            var moscow = new Address();
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
            session.flush();
            session.getTransaction().commit();
        }
    }

    private void readEntities() {
        var session = factory.openSession();
        List<Address> allAddresses = session.createCriteria(Address.class).addOrder(Order.desc("id")).list();
        List<People> allPeoples = session.createCriteria(People.class).addOrder(Order.desc("id")).list();
        session.close();
    }
}
