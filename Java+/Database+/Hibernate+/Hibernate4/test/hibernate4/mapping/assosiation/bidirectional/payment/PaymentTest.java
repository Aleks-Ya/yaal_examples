package hibernate4.mapping.assosiation.bidirectional.payment;

import hibernate4.context.session.HibernateSessionFactory436;
import hibernate4.mapping.assosiation.bidirectional.people.Address;
import hibernate4.mapping.assosiation.bidirectional.people.People;
import org.hamcrest.Matchers;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    private static final HibernateSessionFactory436 factory = HibernateSessionFactory436.makeFactory(
            Payment.class, Transaction.class, Slip.class, People.class, Address.class);

    private Payment expPayment;
    private Transaction expTransaction;
    private Slip expSlipA;
    private Slip expSlipB;

    @Test
    void test() throws Exception {
        try {
            saveEntities();
            loadEntities();
        } finally {
            SessionFactory sessionFactory = factory.getSessionFactory();
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    private void saveEntities() {
        Session session = factory.openSession();

        Payment payment = new Payment("Bike buy");

        Transaction transaction = new Transaction();
//        payment.setTransaction(transaction);
        transaction.setPayment(payment);

        Slip slipA = new Slip(transaction, "С Вас 1 000 $$$");
        Slip slipB = new Slip(transaction, "С Вас $10 000 000");

        Set<Slip> slips = new HashSet<Slip>();
        slips.add(slipA);
        slips.add(slipB);

//        transaction.setSlips(slips);

        session.beginTransaction();
        session.save(payment);
        session.save(transaction);
        session.save(slipA);
        session.save(slipB);
        session.getTransaction().commit();
        session.flush();
        session.close();
        expPayment = payment;
        expTransaction = transaction;
        expSlipA = slipA;
        expSlipB = slipB;
    }

    private void loadEntities() {
        Session session = factory.openSession();
        List<Payment> allPayments = session.createCriteria(Payment.class).addOrder(Order.desc("id")).list();
        List<Transaction> allTransactions = session.createCriteria(Transaction.class).addOrder(Order.desc("id")).list();
        List<Slip> allSlips = session.createCriteria(Slip.class).addOrder(Order.desc("id")).list();

        Payment actPayment = allPayments.get(0);
        Transaction actTransaction = allTransactions.get(0);
        Set<Slip> transactionSlips = actTransaction.getSlips();
        Hibernate.initialize(transactionSlips);

        session.close();

        assertEquals(1, allPayments.size());
        assertEquals(1, allTransactions.size());
        assertEquals(2, allSlips.size());
        assertThat(allSlips, Matchers.containsInAnyOrder(expSlipA, expSlipB));

        assertEquals(expPayment, actPayment);
        assertEquals(expPayment, actTransaction.getPayment());

        assertEquals(expTransaction, actTransaction);
        assertEquals(expTransaction, actPayment.getTransaction());

        assertEquals(2, transactionSlips.size());
        assertThat(transactionSlips, Matchers.containsInAnyOrder(expSlipA, expSlipB));
        for (Slip slip : transactionSlips) {
            assertEquals(expTransaction, slip.getTransaction());
        }

    }
}
