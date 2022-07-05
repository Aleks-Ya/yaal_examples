package hibernate4.mapping.assosiation.bidirectional.payment;

import hibernate4.context.session.HibernateSessionFactory436;
import hibernate4.mapping.assosiation.bidirectional.people.Address;
import hibernate4.mapping.assosiation.bidirectional.people.People;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {
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
            var sessionFactory = factory.getSessionFactory();
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    private void saveEntities() {
        var session = factory.openSession();

        var payment = new Payment("Bike buy");

        var transaction = new Transaction();
//        payment.setTransaction(transaction);
        transaction.setPayment(payment);

        var slipA = new Slip(transaction, "С Вас 1 000 $$$");
        var slipB = new Slip(transaction, "С Вас $10 000 000");

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
        var session = factory.openSession();
        List<Payment> allPayments = session.createCriteria(Payment.class).addOrder(Order.desc("id")).list();
        List<Transaction> allTransactions = session.createCriteria(Transaction.class).addOrder(Order.desc("id")).list();
        List<Slip> allSlips = session.createCriteria(Slip.class).addOrder(Order.desc("id")).list();

        var actPayment = allPayments.get(0);
        var actTransaction = allTransactions.get(0);
        var transactionSlips = actTransaction.getSlips();
        Hibernate.initialize(transactionSlips);

        session.close();

        assertThat(allPayments.size()).isEqualTo(1);
        assertThat(allTransactions.size()).isEqualTo(1);
        assertThat(allSlips.size()).isEqualTo(2);
        assertThat(allSlips).containsExactlyInAnyOrder(expSlipA, expSlipB);

        assertThat(actPayment).isEqualTo(expPayment);
        assertThat(actTransaction.getPayment()).isEqualTo(expPayment);

        assertThat(actTransaction).isEqualTo(expTransaction);
        assertThat(actPayment.getTransaction()).isEqualTo(expTransaction);

        assertThat(transactionSlips.size()).isEqualTo(2);
        assertThat(transactionSlips).containsExactlyInAnyOrder(expSlipA, expSlipB);
        for (var slip : transactionSlips) {
            assertThat(slip.getTransaction()).isEqualTo(expTransaction);
        }

    }
}
